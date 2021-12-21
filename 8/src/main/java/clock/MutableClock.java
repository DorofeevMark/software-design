package clock;

import java.time.Duration;
import java.time.Instant;

public class MutableClock implements Clock {
    private Instant instant;

    public MutableClock(Instant instant) {
        this.instant = instant;
    }

    @Override
    public Instant now() {
        return instant;
    }

    public void addTime(Duration duration) {
        instant = instant.plus(duration);
    }
}