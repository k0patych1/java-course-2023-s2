package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,

    @NotNull
    RetryPolicy retryPolicy
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
}
