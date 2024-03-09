package edu.java.bot.clients;

import edu.java.bot.model.dto.request.AddLinkRequest;
import edu.java.bot.model.dto.request.RemoveLinkRequest;
import edu.java.bot.model.dto.response.LinkResponse;
import edu.java.bot.model.dto.response.ListLinksResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ScrapperClientImpl implements ScrapperClient {

    private static final String SCRAPPER_URL = "https://localhost:8080";
    private static final String LINKS_ENDPOINT = "/links";
    private static final String CHAT_ENDPOINT = "/tg-chat/{id}";

    private static final String CHAT_HEADER = "Tg-Chat-Id";

    private final WebClient webClient;

    public ScrapperClientImpl() {
        webClient = WebClient.create(SCRAPPER_URL);
    }

    public ScrapperClientImpl(String url) {
        webClient = WebClient.create(url);
    }

    @Override
    public String registerChat(Long id) {
        return webClient
            .post()
            .uri(LINKS_ENDPOINT, id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public String deleteChat(Long id) {
        return webClient
            .delete()
            .uri(CHAT_ENDPOINT, id)
            .retrieve()
            .bodyToMono(String.class)
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
            .block();
    }
}
