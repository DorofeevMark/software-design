import clock.MutableClock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import statistic.EventStatistic;
import statistic.EventStatisticImpl;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


public class EventStatisticImplTest {
    private MutableClock clock;
    private EventStatistic eventStatistic;
    private static final double DELTA = 1e-12;

    @Before
    public void before() {
        clock = new MutableClock(Instant.now());
        eventStatistic = new EventStatisticImpl(clock);
    }

    @Test
    public void emptyTest() {
        Assert.assertEquals(eventStatistic.getEventStatisticByName("event"), 0.0, DELTA);
        checkAllEventStatistics(new HashMap<String, Double>());
    }

    @Test
    public void singleTest() {
        eventStatistic.incEvent("event");
        eventStatistic.incEvent("event");
        Assert.assertEquals(eventStatistic.getEventStatisticByName("event"), 2.0 / 60, DELTA);
        checkAllEventStatistics(new HashMap<String, Double>() {{
            put("event", 2.0 / 60);
        }});
    }

    @Test
    public void singleTimedOutTest() {
        eventStatistic.incEvent("event");
        clock.addTime(Duration.ofMinutes(60));
        Assert.assertEquals(eventStatistic.getEventStatisticByName("event"), 0, DELTA);
        checkAllEventStatistics(new HashMap<String, Double>());
    }


    @Test
    public void multipleTimedOutEventTest() {
        eventStatistic.incEvent("event");
        Assert.assertEquals(eventStatistic.getEventStatisticByName("event"), 1.0 / 60, DELTA);
        clock.addTime(Duration.ofMinutes(40));
        eventStatistic.incEvent("event");
        Assert.assertEquals(eventStatistic.getEventStatisticByName("event"), 2.0 / 60, DELTA);
        clock.addTime(Duration.ofMinutes(40));
        Assert.assertEquals(eventStatistic.getEventStatisticByName("event"), 1.0 / 60, DELTA);
    }

    @Test
    public void multipleTimedOutOrNotEventTest() {
        eventStatistic.incEvent("event1");
        eventStatistic.incEvent("event2");
        eventStatistic.incEvent("event2");
        clock.addTime(Duration.ofMinutes(40));
        eventStatistic.incEvent("event2");
        clock.addTime(Duration.ofMinutes(40));
        Assert.assertEquals(eventStatistic.getEventStatisticByName("event1"), 0, DELTA);
        Assert.assertEquals(eventStatistic.getEventStatisticByName("event2"), 1.0 / 60, DELTA);
        checkAllEventStatistics(new HashMap<String, Double>() {{
            put("event2", 1.0 / 60);
        }});
    }

    private void checkAllEventStatistics(Map<String, Double> expected) {
        Map<String, Double> events = eventStatistic.getAllEventStatistic();
        Assert.assertEquals(events.size(), expected.size());
        expected.forEach((name, res) -> Assert.assertEquals(events.get(name), res, DELTA));
    }
}
