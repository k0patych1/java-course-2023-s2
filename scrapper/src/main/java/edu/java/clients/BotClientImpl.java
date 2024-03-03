package edu.java.clients;

import model.AddLinkRequest;
import model.LinkResponse;
import model.RemoveLinkRequest;
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
    public LinkResponse addLink(AddLinkRequest addLinkRequest) {
        return webClient
            .post()
            .uri(UPDATES)
            .body(BodyInserters.fromValue(addLinkRequest))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse removeLink(RemoveLinkRequest removeLinkRequest) {
        return webClient
            .post()
            .uri(UPDATES)
            .body(BodyInserters.fromValue(removeLinkRequest))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
