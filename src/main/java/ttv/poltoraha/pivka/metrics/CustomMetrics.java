package ttv.poltoraha.pivka.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

// Как правило все имеющиеся метрики создаются в отдельном классе.
@Component
public class CustomMetrics {
    private final Counter requestCounter;
    private final Timer dbCallTimer;

    @Autowired
    public CustomMetrics(MeterRegistry registry) {
        this.requestCounter = registry.counter("pivka.counter");
        this.dbCallTimer = registry.timer("pivka.timer");
    }

    public void incrementRequestCounter() {
        requestCounter.increment();
    }

    public void recordDbCallTimer(Runnable dbOperation) {
        try {
            dbCallTimer.record(dbOperation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T recordDbCallTimer(Supplier<T> dbOperation) {
        return dbCallTimer.record(() -> {
            try {
                return dbOperation.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
