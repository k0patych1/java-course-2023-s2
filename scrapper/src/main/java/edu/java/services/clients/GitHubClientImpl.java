package edu.java.services.clients;

import edu.java.models.GitHubRepoLastUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GitHubClientImpl implements GitHubClient {
    private final WebClient webClient;

    @Autowired
    public GitHubClientImpl(@Qualifier("gitHubClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public GitHubRepoLastUpdate fetchUser(String user, String repository) {
        return webClient.get()
            .uri("/repos/{user}/{repository}", user, repository)
            .retrieve()
            .bodyToMono(GitHubRepoLastUpdate.class)
            .block();
    }
}
