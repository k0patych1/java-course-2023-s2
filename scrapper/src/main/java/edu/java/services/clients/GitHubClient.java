package edu.java.services.clients;

import edu.java.models.GitHubLastCommitInMainBranch;
import edu.java.models.GitHubRepoLastUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
public class GitHubClient implements IGitHubClient {
    private final WebClient gitHubWebClient;

    private final Retry retry;

    @Override
    public GitHubRepoLastUpdate fetchRepo(String user, String repository) {
        return gitHubWebClient.get()
            .uri("/repos/{user}/{repository}", user, repository)
            .retrieve()
            .bodyToMono(GitHubRepoLastUpdate.class)
            .retryWhen(retry)
            .block();
    }

    public GitHubLastCommitInMainBranch fetchRepoMainBranch(String user, String repository) {
        return gitHubWebClient.get()
            .uri("/repos/{user}/{repository}/commits/main", user, repository)
            .retrieve()
            .bodyToMono(GitHubLastCommitInMainBranch.class)
            .retryWhen(retry)
            .block();
    }
}
