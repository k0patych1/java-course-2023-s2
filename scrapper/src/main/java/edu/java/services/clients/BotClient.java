package edu.java.services.clients;

import edu.java.models.dto.request.LinkUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
public class BotClient implements IBotClient {
    private final static String ENDPOINT = "/updates";

    private final WebClient botWebClient;

    private final Retry retry;

    @Override
    public String update(LinkUpdate linkUpdate) {
        return botWebClient
            .post()
            .uri(ENDPOINT)
            .body(BodyInserters.fromValue(linkUpdate))
            .retrieve()
            .bodyToMono(String.class)
            .retryWhen(retry)
            .block();
    }
}
