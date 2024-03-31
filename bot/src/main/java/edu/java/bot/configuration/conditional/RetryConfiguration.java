package edu.java.bot.configuration.conditional;

import edu.java.bot.configuration.ApplicationConfig;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

@Configuration
@RequiredArgsConstructor
public class RetryConfiguration {
    private final ApplicationConfig applicationConfig;

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "retry-policy.back-off-type", havingValue = "exp")
    public Retry exponentialRetry() {
        Long maxAttempts = applicationConfig.retryPolicy().maxAttempts();
        Duration delay = applicationConfig.retryPolicy().delay();

        return Retry.backoff(maxAttempts, delay)
            .filter(this::filter)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "retry-policy.back-off-type", havingValue = "const")
    public Retry constantRetry() {
        Long maxAttempts = applicationConfig.retryPolicy().maxAttempts();
        Duration delay = applicationConfig.retryPolicy().delay();

        return Retry.fixedDelay(maxAttempts, delay)
            .filter(this::filter)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());

    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "retry-policy.back-off-type", havingValue = "linear")
    public Retry linearRetry() {
        Duration delay = applicationConfig.retryPolicy().delay();
        Long maxAttempts = applicationConfig.retryPolicy().maxAttempts();

        return new LinearRetry(maxAttempts, delay)
            .filter(this::filter)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

    private boolean filter(Throwable throwable) {
        return throwable instanceof WebClientResponseException exception
            && applicationConfig.retryPolicy()
            .retryCodes()
            .contains(exception.getStatusCode().value());
    }
}
