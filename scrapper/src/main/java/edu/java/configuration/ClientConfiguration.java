package edu.java.configuration;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    private static final String GIT_HUB_BASE_URL = "https://api.github.com";
    private static final String STACK_OVER_FLOW_BASE_URL = "https://api.stackexchange.com";

    @Bean
    public WebClient gitHubClient(ApplicationConfig applicationConfig) {
        String baseUrl = Optional.ofNullable(applicationConfig.gitHubBaseUrl())
            .orElse(GIT_HUB_BASE_URL);

        return WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Bean
    public WebClient stackOverflowClient(ApplicationConfig applicationConfig) {
        String baseUrl = Optional.ofNullable(applicationConfig.stackOverFlowBaseUrl())
            .orElse(STACK_OVER_FLOW_BASE_URL);

        return WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }
}
