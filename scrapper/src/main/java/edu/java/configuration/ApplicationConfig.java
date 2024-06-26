package edu.java.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    Scheduler scheduler,

    @NotEmpty
    String gitHubBaseUrl,

    @NotEmpty
    String stackOverFlowBaseUrl,

    @NotNull
    Duration intervalCheckTime,

    @NotNull
    AccessType databaseAccessType,

    @NotNull
    RetryPolicy retryPolicy,

    @NotNull
    RateLimiting rateLimiting,

    @NotNull
    Boolean useQueue,

    @NotNull
    Kafka kafka
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public enum AccessType {
        JDBC,
        JPA,
        JOOQ
    }

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
        String topic,

        @NotEmpty
        String bootstrapServers,

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
        Integer maxInFlightPerConnection,

        @NotNull
        Boolean enableIdempotence
    ) {

    }
}
