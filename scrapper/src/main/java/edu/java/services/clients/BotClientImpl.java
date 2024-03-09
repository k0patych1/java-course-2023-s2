package edu.java.services.clients;

import edu.java.models.dto.request.LinkUpdate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BotClientImpl implements BotClient {
    private final static String BOT_URL = "https://localhost:8090";
    private final static String UPDATES = "/updates";
    private final WebClient webClient;

    public BotClientImpl() {
        webClient = WebClient.create(BOT_URL);
    }

    public BotClientImpl(String url) {
        webClient = WebClient.create(url);
    }

    @Override
    public String update(LinkUpdate linkUpdate) {
        return webClient
            .post()
            .uri(UPDATES)
            .body(BodyInserters.fromValue(linkUpdate))
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
