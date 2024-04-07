package edu.java.configuration.conditional.rateLimit;

import edu.java.configuration.ApplicationConfig;
import edu.java.controllers.interceptor.RateLimitInterceptor;
import edu.java.services.rateLimit.RateLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "rate-limiter.enable", havingValue = "true")
@RequiredArgsConstructor
public class RateLimitConfiguration {
    private final ApplicationConfig applicationConfig;

    @Bean
    public RateLimitService rateLimitService() {
        return new RateLimitService(applicationConfig);
    }

    @Bean
    public RateLimitInterceptor rateLimitInterceptor() {
        return new RateLimitInterceptor(rateLimitService());
    }
}
