package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;
import org.jooq.impl.QOM;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,

    @NotNull
    RetryPolicy retryPolicy,

    @NotNull
    RateLimiting rateLimiting,

    @NotNull
    Kafka kafka,

    @NotNull
    Micrometer micrometer
) {
    public record RetryPolicy(
        @NotNull
        BackOffType backOffType,

        @NotNull
        Long maxAttempts,

        @NotNull
        Duration delay,

        @NotNull
        Set<Integer> retryCodes

    ) {
        public enum BackOffType {
            CONST,
            LINEAR,
            EXP
        }
    }

    public record RateLimiting(
        @NotNull
        boolean enable,

        @NotNull
        Long tokensCapacity,

        @NotNull
        Long tokensPerSecond
    ) {

    }

    public record Kafka(
        @NotEmpty
        String groupId,

        @NotEmpty
        String bootstrapServers,

        @NotEmpty
        String autoOffsetReset,

        @NotNull
        Integer maxPollIntervalMs,

        @NotNull
        Boolean enableAutoCommit,

        @NotNull
        Integer concurrency,

        @NotNull
        DLQProperties dlq
    ) {
        public record DLQProperties(
            @NotEmpty
            String topic,

            @NotNull
            Integer replicationFactor,

            @NotNull
            Integer partitions,

            @NotEmpty
            String acksMode,

            @NotNull
            Integer deliveryTimeout,

            @NotNull
            Integer linger,

            @NotNull
            Integer batchSize,

            @NotNull
            Integer maxInFlightPerConnection
        ) {

        }
    }

    public record Micrometer(
        ProcessedMessagesCounter processedMessagesCounter
    ) {
        public record ProcessedMessagesCounter(
            String name,
            String description
        ) {
        }
    }
}
