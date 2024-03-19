package edu.java.scrapper;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import edu.java.models.GitHubLastCommitInMainBranch;
import edu.java.models.StackOverFlowLastAnswer;
import edu.java.models.dto.Link;
import edu.java.models.dto.request.LinkUpdate;
import edu.java.services.clients.BotClient;
import edu.java.services.clients.GitHubClient;
import edu.java.services.clients.IBotClient;
import edu.java.services.clients.IGitHubClient;
import edu.java.services.clients.IStackOverFlowClient;
import edu.java.services.clients.StackOverFlowClient;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Arrays;
import edu.java.services.parsers.GitHubUrlParser;
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
    public void gitHubClientLastUpdateTest() {
        wireMockRule.start();

        String user = "user";
        String repository = "test";

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

        assertThat(gitHubClient.fetchRepo(user, repository).name())
            .isEqualTo("test");

        assertThat(gitHubClient.fetchRepo(user, repository).time())
            .isEqualTo("2023-02-06T04:58:53Z");

        wireMockRule.stop();
    }

    @Test
    public void gitHubClientTest() {
        wireMockRule.start();

        String user = "user";
        String repository = "tink";

        String baseMockUrl = wireMockRule.baseUrl();
        String endpoint = "/repos/" + user + '/' + repository + "/commits/main";

        String body = """
            {
              "commit": {
                "committer": {
                  "name": "k0patych1",
                  "date": "2024-03-17T14:22:29Z"
                },
                "message": "second"
              },
              "stats": {
                "additions": 1,
                "deletions": 2
              }
            }
            """;

        stubForGet(endpoint, body);

        WebClient mockWebClient = WebClient.builder()
            .baseUrl(baseMockUrl)
            .build();

        IGitHubClient gitHubClient = new GitHubClient(mockWebClient);


        GitHubLastCommitInMainBranch lastCommit = gitHubClient.fetchRepoMainBranch(user, repository);

        assertThat(lastCommit.commit().committer().name())
            .isEqualTo("k0patych1");

        assertThat(lastCommit.commit().message())
            .isEqualTo("second");

        assertThat(lastCommit.stats().additions())
            .isEqualTo(1);

        assertThat(lastCommit.stats().deletions())
            .isEqualTo(2);

        assertThat(lastCommit.commit().committer().time())
            .isEqualTo("2024-03-17T14:22:29Z");

        wireMockRule.stop();
    }

    @Test
    public void stackOverFlowClientTest() {
        wireMockRule.start();

        String questionId = "239";

        String baseMockUrl = wireMockRule.baseUrl();
        String endpoint = "/questions/" + questionId + "/answers?order=desc&sort=activity&site=stackoverflow&filter=withbody";

        String body = """
            {
                   "items": [
                     {
                       "owner": {
                         "display_name": "Afsar"
                       },
                       "score": 10,
                       "creation_date": 1709759290                     }
                   ]
                 }
            """;

        stubForGet(endpoint, body);

        WebClient mockWebClient = WebClient.builder()
            .baseUrl(baseMockUrl)
            .build();

        IStackOverFlowClient gitHubClient = new StackOverFlowClient(mockWebClient);

        StackOverFlowLastAnswer.Answer answer = gitHubClient.fetchQuestion(questionId)
            .answerList()
            .getFirst();

        assertThat(answer.time())
            .isEqualTo("2024-03-06T21:08:10Z");

        assertThat(answer.owner().displayName())
            .isEqualTo("Afsar");

        assertThat(answer.score())
            .isEqualTo(10);

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
