package edu.java.scrapper;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import edu.java.models.dto.request.LinkUpdate;
import edu.java.services.clients.BotClient;
import edu.java.services.clients.GitHubClient;
import edu.java.services.clients.IBotClient;
import edu.java.services.clients.IGitHubClient;
import edu.java.services.clients.IStackOverFlowClient;
import edu.java.services.clients.StackOverFlowClient;
import java.net.URI;
import java.util.Arrays;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ClientsTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    public void stubForGet(String endpoint, String body) {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(endpoint))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(body)
                .withStatus(200)));
    }

    public void stubForPost(String endpoint, String body) {
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(endpoint))
            .willReturn(aResponse()
                .withBody(body)
                .withStatus(200)));
    }

    @Test
    public void gitHubTest() {
        wireMockRule.start();

        String user = "user";
        String repository = "tink";

        String baseMockUrl = wireMockRule.baseUrl();
        String endpoint = "/repos/" + user + '/' + repository;

        String body = """
                {
                "id": 1,
                "name": "test",
                "updated_at": "2023-02-06T04:58:53Z",
                "pushed_at": "2023-02-25T14:25:39Z"
                }
                """;

        stubForGet(endpoint, body);

        WebClient mockWebClient = WebClient.builder()
            .baseUrl(baseMockUrl)
            .build();

        IGitHubClient gitHubClient = new GitHubClient(mockWebClient);

        assertThat(gitHubClient.fetchRepo(user, repository).name()).isEqualTo("test");
        assertThat(gitHubClient.fetchRepo(user, repository).time()).isEqualTo("2023-02-06T04:58:53Z");

        wireMockRule.stop();
    }

    @Test
    public void stackOverFlowTest() {
        wireMockRule.start();

        String questionId = "239";

        String baseMockUrl = wireMockRule.baseUrl();
        String endpoint = "/questions/" + questionId + "?order=desc&sort=activity&site=stackoverflow";

        String body = """
            {
              "items": [
                {
                  "owner": {
                    "account_id": 6190832,
                    "reputation": 1,
                    "user_id": 8864250,
                    "user_type": "registered",
                    "profile_image": "https://www.gravatar.com/avatar/99d6dabd828c69adff3956c9167a06de?s=256&d=identicon&r=PG&f=y&so-version=2",
                    "display_name": "Ivan Ivanov",
                    "link": "https://stackoverflow.com/users/8864250/ivan-ivanov"
                  },
                  "is_accepted": false,
                  "score": -1,
                  "last_activity_date": 1708876663,
                  "last_edit_date": 1708876663,
                  "creation_date": 1708873762,
                  "answer_id": 78056465,
                  "question_id": 6429462,
                  "content_license": "CC BY-SA 4.0"
                }
              ],
              "has_more": true,
              "quota_max": 10000,
              "quota_remaining": 9953
            }
            """;

        stubForGet(endpoint, body);

        WebClient mockWebClient = WebClient.builder()
            .baseUrl(baseMockUrl)
            .build();

        IStackOverFlowClient gitHubClient = new StackOverFlowClient(mockWebClient);

        assertThat(gitHubClient.fetchQuestion(questionId)
            .answerList().
            getFirst()
            .time())
            .isEqualTo("2024-02-25T15:57:43Z");

        wireMockRule.stop();
    }

    @Test
    public void botClientTest() {
        wireMockRule.start();

        String baseMockUrl = wireMockRule.baseUrl();

        WebClient mockWebClient = WebClient.builder()
            .baseUrl(baseMockUrl)
            .build();

        IBotClient botClient = new BotClient(mockWebClient);

        String endpoint = "/updates";
        String body = "OK";

        stubForPost(endpoint, body);

        LinkUpdate linkUpdate = new LinkUpdate();
        linkUpdate.setId(2018L);
        linkUpdate.setUrl(URI.create("https://stackoverflow.com/questions/"));
        linkUpdate.setDescription("20!8");
        linkUpdate.setTgChatIds(Arrays.asList(2L, 4L));

        String response = botClient.update(linkUpdate);

        assertThat(response).isEqualTo("OK");

        wireMockRule.stop();
    }
}
