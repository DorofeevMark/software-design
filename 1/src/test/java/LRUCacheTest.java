import com.LRUCache;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class LRUCacheTest {
    private LRUCache<Integer, Integer> cache;

    @Before
    public void before() {
        cache = new LRUCache<>(10);
    }

    @Test(expected = AssertionError.class)
    public void testEmptyIsNotAllowed() {
        LRUCache<String, String> lru = new LRUCache<>(0);
    }

    @Test
    public void testSimpleActions() {
        Random r = new Random();

        int k = r.nextInt();
        int v = r.nextInt();
        cache.put(k, v);

        int storedV = cache.get(k);
        assertEquals(v, storedV);
        cache.remove(k);
        assertNull(cache.get(k));

        cache.put(k, v);
        cache.put(k, v);
        assertEquals(v, cache.get(k).intValue());
    }

    @Test
    public void testSimpleActionsSet() {
        final int size = 100;
        final int iters = 10000;

        cache = new LRUCache<>(size);
        Random r = new Random();

        for (int i = 0; i < iters; ++i) {
            Integer k = r.nextInt();
            Integer v = r.nextInt();
            cache.put(k, v);
            assertEquals(v, cache.get(k));
        }
    }

    @Test
    public void testMapFunctionality() {
        final int size = 1000;

        cache = new LRUCache<>(size);
        HashMap<Integer, Integer> map = new HashMap<>();
        Random r = new Random();

        for (int i = 0; i < size; ++i) {
            int k = r.nextInt();
            int v = r.nextInt();
            map.put(k, v);
            cache.put(k, v);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            assertEquals(entry.getValue(), cache.get(entry.getKey()));
            cache.remove(entry.getKey());
        }

        assertEquals(0, cache.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullValue() {
        cache = new LRUCache<>(1);
        cache.put(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullKey() {
        cache = new LRUCache<>(1);
        cache.put(null, 1);
    }

    @Test
    public void testOnlyLast() {
        final int size = 1;
        final int iters = 10000;

        cache = new LRUCache<>(size);
        Random r = new Random();

        int k = 0, v = 0;
        for (int i = 0; i < iters; ++i) {
            k = r.nextInt();
            v = r.nextInt();
            cache.put(k, v);
        }
        assertEquals(v, cache.get(k).intValue());
        assertEquals(1, cache.size());
    }

    @Test
    public void testLeastRecentlyUsed() {
        final int rounds = 100;
        final int size = 1000;
        cache = new LRUCache<>(size);

        for (int r = 0; r < rounds; ++r) {
            for (int ind = 0; ind < size; ++ind) {
                int cur = ind + size * r;
                cache.put(cur, cur);
            }

            for (int ind = 0; ind < size; ++ind) {
                int cur = ind + size * r;
                assertNotNull(cache.get(cur));
            }
            assertEquals(size, cache.size());
        }
    }

    @Test
    public void testLeastRecentlyUsedRandomized() {
        final int rounds = 10000;
        final int size = 100;
        final int bound = 500;

        cache = new LRUCache<>(size);
        ArrayList<Integer> values = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < bound; ++i) {
            values.add(i);
        }

        for (int r = 0; r < rounds; ++r) {
            int ind = rand.nextInt(values.size());
            int next = values.get(ind);
            if (rand.nextBoolean()) {
                if (cache.get(next) != null) {
                    values.add(next);
                }
            } else {
                cache.put(next, next);
                values.add(next);
            }
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = values.size() - 1; i >= 0; --i) {
            int value = values.get(i);
            if (map.get(value) != null || map.size() < size) {
                assertNotNull(cache.get(value));
                map.put(value, value);
            } else {
                assertNull(cache.get(value));
            }
        }
    }
}
