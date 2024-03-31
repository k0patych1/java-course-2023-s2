package edu.java.bot.configuration.conditional.retry;

import java.time.Duration;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class LinearRetry extends Retry {
    private final Long maxAttempts;

    private final Duration delay;

    private Predicate<Throwable> filter;

    private BiFunction<LinearRetry, RetrySignal, Throwable> retryExhaustedGenerator;

    public LinearRetry(Long maxAttempts, Duration delay) {
        this.maxAttempts = maxAttempts;
        this.delay = delay;
    }

    public LinearRetry filter(Predicate<Throwable> filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public Publisher<?> generateCompanion(Flux<RetrySignal> retrySignals) {
        return retrySignals.flatMap(this::getRetry);
    }

    public LinearRetry onRetryExhaustedThrow(
        BiFunction<LinearRetry, RetrySignal, Throwable> retryExhaustedGenerator
    ) {
        this.retryExhaustedGenerator = retryExhaustedGenerator;
        return this;
    }

    private Mono<Long> getRetry(RetrySignal rs) {
        if (!filter.test(rs.failure()) || rs.totalRetries() >= maxAttempts) {
            return Mono.error(retryExhaustedGenerator.apply(this, rs));
        }

        Duration curDelay = delay.multipliedBy(rs.totalRetries());
        return Mono.delay(curDelay).thenReturn(rs.totalRetries());
    }
}
