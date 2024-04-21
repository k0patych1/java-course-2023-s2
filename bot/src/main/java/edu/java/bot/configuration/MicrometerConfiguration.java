package edu.java.bot.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfiguration {
    @Bean
    public Counter processedMessagesCounter(MeterRegistry registry, ApplicationConfig applicationConfig) {
        String name = applicationConfig.micrometer().processedMessagesCounter().name();
        String description = applicationConfig.micrometer().processedMessagesCounter().description();

        return Counter.builder(name)
            .description(description)
            .register(registry);
    }
}
