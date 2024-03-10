package edu.java.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    private static final String SCRAPPER_CLIENT_URL = "https://localhost:8080";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl(SCRAPPER_CLIENT_URL)
            .build();
    }
}
