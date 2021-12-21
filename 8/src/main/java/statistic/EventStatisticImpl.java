package statistic;

import clock.Clock;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class EventStatisticImpl implements EventStatistic {
    private static final double MINUTES_IN_HOUR = Duration.ofHours(1).toMinutes();
    private static final double MILLIS_IN_HOUR = Duration.ofHours(1).toMillis();
    private final Clock clock;
    private final Queue<Event> eventQueue = new ArrayDeque<>();
    private final Map<String, Integer> eventCount = new HashMap<>();

    public EventStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    private void deleteOldEvent(long nowInMillis) {
        while (!eventQueue.isEmpty()) {
            Event tmp = eventQueue.peek();
            if (tmp.getTime() + MILLIS_IN_HOUR <= nowInMillis) {
                eventQueue.remove();
                Integer count = eventCount.get(tmp.getName());
                if (count == 1) {
                    eventCount.remove(tmp.getName());
                } else {
                    eventCount.put(tmp.getName(), count - 1);
                }
            } else {
                break;
            }
        }
    }

    private double getRpm(Integer count) {
        return count == null ? 0 : count / MINUTES_IN_HOUR;
    }

    @Override
    public void incEvent(String name) {
        long now = clock.now().toEpochMilli();
        deleteOldEvent(now);
        eventQueue.add(new Event(name, now));
        eventCount.putIfAbsent(name, 0);
        eventCount.put(name, eventCount.get(name) + 1);
    }

    @Override
    public double getEventStatisticByName(String name) {
        long now = clock.now().toEpochMilli();
        deleteOldEvent(now);
        Integer count = eventCount.get(name);
        return count == null ? 0 : count / MINUTES_IN_HOUR;
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        long now = clock.now().toEpochMilli();
        deleteOldEvent(now);
        Map<String, Double> newCount = new HashMap<>();
        for (Map.Entry<String, Integer> entry : eventCount.entrySet()) {
            Integer count = entry.getValue();
            newCount.put(entry.getKey(), count == null ? 0 : count / MINUTES_IN_HOUR);
        }
        return newCount;
    }

    @Override
    public void printStatistic() {
        long now = clock.now().toEpochMilli();
        deleteOldEvent(now);
        for (Map.Entry<String, Integer> entry : eventCount.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    private static class Event {
        private final String name;
        private final long time;

        public Event(String name, long time) {
            this.name = name;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public long getTime() {
            return time;
        }
    }
}
