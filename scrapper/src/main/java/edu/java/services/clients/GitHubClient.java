package edu.java.services.clients;

import edu.java.models.GitHubLastCommitInMainBranch;
import edu.java.models.GitHubRepoLastUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GitHubClient implements IGitHubClient {
    private final WebClient webClient;

    @Autowired
    public GitHubClient(@Qualifier("gitHubWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public GitHubRepoLastUpdate fetchRepo(String user, String repository) {
        return webClient.get()
            .uri("/repos/{user}/{repository}", user, repository)
            .retrieve()
            .bodyToMono(GitHubRepoLastUpdate.class)
            .block();
    }

    public GitHubLastCommitInMainBranch fetchRepoMainBranch(String user, String repository) {
        return webClient.get()
            .uri("/repos/{user}/{repository}/commits/main", user, repository)
            .retrieve()
            .bodyToMono(GitHubLastCommitInMainBranch.class)
            .block();
    }
}
