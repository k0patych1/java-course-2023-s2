package edu.java.configuration;

import java.util.Optional;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    private static final String GIT_HUB_BASE_URL = "https://api.github.com";
    private static final String STACK_OVER_FLOW_BASE_URL = "https://api.stackexchange.com";
    private static final String BOT_URL = "http://localhost:8090";

    @Bean
    public WebClient gitHubWebClient(ApplicationConfig applicationConfig) {
        String baseUrl = Optional.ofNullable(applicationConfig.gitHubBaseUrl())
            .orElse(GIT_HUB_BASE_URL);

        return WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Bean
    public WebClient stackOverflowWebClient(ApplicationConfig applicationConfig) {
        String baseUrl = Optional.ofNullable(applicationConfig.stackOverFlowBaseUrl())
            .orElse(STACK_OVER_FLOW_BASE_URL);

        return WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Bean
    public WebClient botWebClient() {
        return WebClient.builder()
            .baseUrl(BOT_URL)
            .build();
    }

    @Bean
    public DefaultConfigurationCustomizer postgresJooqCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
            .withRenderSchema(false)
            .withRenderFormatted(true)
            .withRenderQuotedNames(RenderQuotedNames.NEVER);
    }
}
