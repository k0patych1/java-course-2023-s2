package edu.java.scrapper.retry;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import edu.java.models.dto.request.LinkUpdate;
import edu.java.services.clients.BotClient;
import java.util.Set;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.util.retry.Retry;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class LinearRetryTest {
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("app.retry-policy.back-off-type", () -> "linear");
        registry.add("app.retry-policy.max-attempts", () -> 3);
        registry.add("app.retry-policy.delay", () -> 100);
        registry.add("app.retry-policy.retry-codes", () -> Set.of(502, 503));
    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    private void stubForSuccessfullyPost(String endpoint) {
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(endpoint)).inScenario("Test Scenario")
            .whenScenarioStateIs(STARTED)
            .willReturn(aResponse().withStatus(502))
            .willSetStateTo("One Attempt"));
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(endpoint)).inScenario("Test Scenario")
            .whenScenarioStateIs("One Attempt")
            .willReturn(aResponse().withStatus(503))
            .willSetStateTo("Two Attempts"));
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(endpoint)).inScenario("Test Scenario")
            .whenScenarioStateIs("Two Attempts")
            .willReturn(aResponse().withStatus(200)));
    }

    private void stubForFailurePost(String endpoint) {
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(endpoint)).inScenario("Test Scenario")
            .whenScenarioStateIs(STARTED)
            .willReturn(aResponse().withStatus(502))
            .willSetStateTo("One Attempt"));
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(endpoint)).inScenario("Test Scenario")
            .whenScenarioStateIs("One Attempt")
            .willReturn(aResponse().withStatus(503))
            .willSetStateTo("Two Attempts"));
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(endpoint)).inScenario("Test Scenario")
            .whenScenarioStateIs("Two Attempts")
            .willReturn(aResponse().withStatus(404)));
    }

    @Autowired
    private Retry retry;

    @Test
    public void botClientExponentialRetryTestSuccess() {
        wireMockRule.start();

        String baseMockUrl = wireMockRule.baseUrl();
        String endpoint = "/updates";

        stubForSuccessfullyPost(endpoint);

        WebClient mockWebClient = WebClient.builder()
            .baseUrl(baseMockUrl)
            .build();

        BotClient botClient = new BotClient(mockWebClient, retry);
        assertDoesNotThrow(() -> botClient.update(new LinkUpdate()));

        WireMock.verify(3, postRequestedFor(urlEqualTo("/updates")));

        wireMockRule.stop();
    }

    @Test
    public void botClientExponentialRetryFailureTest() {
        wireMockRule.start();

        String baseMockUrl = wireMockRule.baseUrl();
        String endpoint = "/updates";

        stubForFailurePost(endpoint);

        WebClient mockWebClient = WebClient.builder()
            .baseUrl(baseMockUrl)
            .build();

        BotClient botClient = new BotClient(mockWebClient, retry);
        assertThrows(WebClientException.class, () -> botClient.update(new LinkUpdate()));

        WireMock.verify(3, postRequestedFor(urlEqualTo("/updates")));

        wireMockRule.stop();
    }
}
