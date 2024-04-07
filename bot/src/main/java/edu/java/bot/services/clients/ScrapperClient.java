package edu.java.bot.services.clients;

import edu.java.bot.models.dto.request.AddLinkRequest;
import edu.java.bot.models.dto.request.RemoveLinkRequest;
import edu.java.bot.models.dto.response.LinkResponse;
import edu.java.bot.models.dto.response.ListLinksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
public class ScrapperClient implements IScrapperClient {
    private static final String LINKS_ENDPOINT = "/links";
    private static final String CHAT_ENDPOINT = "/tg-chat/{id}";

    private static final String CHAT_HEADER = "Tg-Chat-Id";

    private final WebClient webClient;

    private final Retry retry;

    @Override
    public String registerChat(Long id) {
        return webClient
            .post()
            .uri(CHAT_ENDPOINT, id)
            .retrieve()
            .bodyToMono(String.class)
            .retryWhen(retry)
            .block();
    }

    @Override
    public String deleteChat(Long id) {
        return webClient
            .delete()
            .uri(CHAT_ENDPOINT, id)
            .retrieve()
            .bodyToMono(String.class)
            .retryWhen(retry)
            .block();
    }

    @Override
    public LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return webClient
            .method(HttpMethod.POST)
            .uri(LINKS_ENDPOINT)
            .header(CHAT_HEADER, String.valueOf(tgChatId))
            .body(BodyInserters.fromValue(addLinkRequest))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .retryWhen(retry)
            .block();
    }

    @Override
    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return webClient
            .method(HttpMethod.DELETE)
            .uri(LINKS_ENDPOINT)
            .header(CHAT_HEADER, String.valueOf(tgChatId))
            .body(BodyInserters.fromValue(removeLinkRequest))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .retryWhen(retry)
            .block();
    }

    @Override
    public ListLinksResponse getLinks(Long tgChatId) {
        return webClient
            .get()
            .uri(LINKS_ENDPOINT)
            .header(CHAT_HEADER, String.valueOf(tgChatId))
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .retryWhen(retry)
            .block();
    }
}
