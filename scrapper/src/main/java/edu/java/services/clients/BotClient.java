package edu.java.services.clients;

import edu.java.models.dto.request.LinkUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BotClient implements IBotClient {
    private final static String ENDPOINT = "/updates";
    private final WebClient webClient;

    @Autowired
    public BotClient(@Qualifier("botWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public String update(LinkUpdate linkUpdate) {
        return webClient
            .post()
            .uri(ENDPOINT)
            .body(BodyInserters.fromValue(linkUpdate))
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
