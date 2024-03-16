package edu.java.bot;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import edu.java.bot.models.dto.response.LinkResponse;
import edu.java.bot.models.dto.response.ListLinksResponse;
import edu.java.bot.services.clients.IScrapperClient;
import edu.java.bot.services.clients.ScrapperClient;
import java.net.URI;
import java.util.List;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ScrapperClientTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void registerChatTest() {
        wireMockRule.start();

        String baseMockUrl = wireMockRule.baseUrl();

        WebClient mockWebClient = WebClient.builder()
            .baseUrl(baseMockUrl)
            .build();

        IScrapperClient scrapperClient = new ScrapperClient(mockWebClient);

        String endpoint = "/tg-chat/239";
        String body = "OK";

        stubFor(post(urlEqualTo(endpoint))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody(body)));

        String response = scrapperClient.registerChat(239L);

        assertThat(response).isEqualTo(body);

        wireMockRule.stop();
    }

    @Test
    public void deleteChatTest() {
        wireMockRule.start();

        String baseMockUrl = wireMockRule.baseUrl();

        WebClient mockWebClient = WebClient.builder()
            .baseUrl(baseMockUrl)
            .build();

        IScrapperClient scrapperClient = new ScrapperClient(mockWebClient);

        String endpoint = "/tg-chat/239";
        String body = "OK";

        stubFor(delete(urlEqualTo(endpoint))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody(body)));

        String response = scrapperClient.deleteChat(239L);

        assertThat(response).isEqualTo(body);

        wireMockRule.stop();
    }

    @Test
    public void getLinkTest() {
        wireMockRule.start();

        String baseMockUrl = wireMockRule.baseUrl();

        WebClient mockWebClient = WebClient.builder()
            .baseUrl(baseMockUrl)
            .build();

        IScrapperClient scrapperClient = new ScrapperClient(mockWebClient);

        String endpoint = "/links";
        String body = """
            {
                "links": [
                    {
                        "id": 1,
                        "url": "https://leetcode.com"
                    },
                    {
                        "id": 2,
                        "url": "https://my.itmo.ru"
                    }],
                "size": 2
            }
            """;

        stubFor(WireMock.get(urlEqualTo(endpoint))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(body)));

        ListLinksResponse response = scrapperClient.getLinks(239L);

        LinkResponse response1 = new LinkResponse();
        response1.setId(1L);
        response1.setUrl(URI.create("https://leetcode.com"));

        LinkResponse response2 = new LinkResponse();
        response2.setId(2L);
        response2.setUrl(URI.create("https://my.itmo.ru"));

        ListLinksResponse expectedResponse = new ListLinksResponse();
        expectedResponse.setLinks(List.of(response1, response2));
        expectedResponse.setSize(2);

        assertThat(response).isEqualTo(expectedResponse);

        wireMockRule.stop();
    }
}
