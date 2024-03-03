package edu.java.bot.clients;

import edu.java.models.dto.request.AddLinkRequest;
import edu.java.models.dto.request.RemoveLinkRequest;
import edu.java.models.dto.response.LinkResponse;
import edu.java.models.dto.response.ListLinksResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ScrapperClientImpl implements ScrapperClient {

    private final static String SCRAPPER_URL = "https://localhost:8080";
    private final static String LINKS_ENDPOINT = "/links";
    private final static String CHAT_ENDPOINT = "/tg-chat/{id}";

    private final static String CHAT_HEADER = "Tg-Chat-Id";

    private final WebClient webClient;

    public ScrapperClientImpl() {
        webClient = WebClient.create(SCRAPPER_URL);
    }

    public ScrapperClientImpl(String url) {
        webClient = WebClient.create(url);
    }

    @Override
    public String registerChat(Long id) {
        return webClient.post().uri(LINKS_ENDPOINT, id).retrieve().bodyToMono(String.class).block();
    }

    @Override
    public String deleteChat(Long id) {
        return webClient.delete().uri(CHAT_ENDPOINT, id).retrieve().bodyToMono(String.class).block();
    }

    @Override
    public ListLinksResponse getLinks(Long id) {
        return webClient.get()
            .uri(LINKS_ENDPOINT)
            .header(CHAT_HEADER, String.valueOf(id))
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @Override
    public LinkResponse addLink(Long id, AddLinkRequest addLinkRequest) {
        return webClient
            .post()
            .uri(LINKS_ENDPOINT)
            .header(CHAT_HEADER, String.valueOf(id))
            .body(BodyInserters.fromValue(addLinkRequest))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse removeLink(Long id, RemoveLinkRequest removeLinkRequest) {
        return webClient
            .method(HttpMethod.DELETE)
            .uri(LINKS_ENDPOINT)
            .header(CHAT_HEADER, String.valueOf(id))
            .body(BodyInserters.fromValue(removeLinkRequest))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
