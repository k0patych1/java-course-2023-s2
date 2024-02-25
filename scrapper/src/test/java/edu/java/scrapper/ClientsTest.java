package edu.java.scrapper;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import edu.java.services.clients.GitHubClient;
import edu.java.services.clients.GitHubClientImpl;
import edu.java.services.clients.StackOverFlowClient;
import edu.java.services.clients.StackOverFlowClientImpl;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ClientsTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().dynamicPort());

    public void stubFor(String url, String body) {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(body)
                .withStatus(200)));
    }

    @Test
    public void gitHubTest() {
        String user = "user";
        String repository = "tink";

        String baseMockUrl = wireMockRule.baseUrl();
        String url = "/repos/" + user + '/' + repository;

        String body = """
                {
                "id": 1,
                "name": "test",
                "updated_at": "2023-02-06T04:58:53Z",
                "pushed_at": "2023-02-25T14:25:39Z"
                }
                """;

        stubFor(url, body);

        WebClient mockWebClient = WebClient.builder().baseUrl(baseMockUrl).build();

        GitHubClient gitHubClient = new GitHubClientImpl(mockWebClient);

        assertThat(gitHubClient.fetchUser(user, repository).name()).isEqualTo("test");
        assertThat(gitHubClient.fetchUser(user, repository).time()).isEqualTo("2023-02-06T04:58:53Z");
    }

    @Test
    public void stackOverFlowTest() {
        String questionId = "239";

        String baseMockUrl = wireMockRule.baseUrl();
        String url = "/questions/" + questionId + "?order=desc&sort=activity&site=stackoverflow";

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

        stubFor(url, body);

        WebClient mockWebClient = WebClient.builder().baseUrl(baseMockUrl).build();

        StackOverFlowClient gitHubClient = new StackOverFlowClientImpl(mockWebClient);

        assertThat(gitHubClient.fetchQuestion(questionId).answerList().getFirst().time())
            .isEqualTo("2024-02-25T15:57:43Z");
    }
}
