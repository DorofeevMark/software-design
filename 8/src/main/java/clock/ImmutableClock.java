package clock;

import java.time.Instant;

public class ImmutableClock implements Clock {
    @Override
    public Instant now() {
        return Instant.now();
    }
}
