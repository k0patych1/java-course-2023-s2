package edu.java.services.clients;

import edu.java.models.GitHubLastCommitInMainBranch;
import edu.java.models.GitHubRepoLastUpdate;
import edu.java.models.dto.Link;
import edu.java.services.parsers.GitHubUrlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GitHubClient implements IGitHubClient {
    private final WebClient webClient;

    private final GitHubUrlParser urlParser;

    @Autowired
    public GitHubClient(@Qualifier("gitHubWebClient") WebClient webClient, GitHubUrlParser urlParser) {
        this.webClient = webClient;
        this.urlParser = urlParser;
    }

    @Override
    public GitHubRepoLastUpdate fetchRepo(Link link) {
        String user = urlParser.getUserName(link.getUrl());
        String repository = urlParser.getRepositoryName(link.getUrl());

        return webClient.get()
            .uri("/repos/{user}/{repository}", user, repository)
            .retrieve()
            .bodyToMono(GitHubRepoLastUpdate.class)
            .block();
    }

    @Override
    public String getInfoAboutUpdate(Link link) {
        String user = urlParser.getUserName(link.getUrl());
        String repository = urlParser.getRepositoryName(link.getUrl());
        StringBuilder message = new StringBuilder();
        message.append("Repository: \"")
            .append(link.getUrl())
            .append("\" was updated\n");
        GitHubLastCommitInMainBranch lastCommitInMainBranch = fetchRepoMainBranch(user, repository);
        if (lastCommitInMainBranch.commit().committer().time().isAfter(link.getLastCheckTime())) {
            message.append("New commit in main branch:")
                .append("\n")
                .append("Name: ")
                .append(lastCommitInMainBranch.commit().message())
                .append("\n")
                .append("Committer: ")
                .append(lastCommitInMainBranch.commit().committer().name())
                .append("\n")
                .append("Time : ")
                .append(lastCommitInMainBranch.commit().committer().time())
                .append("\n")
                .append("Additions : ")
                .append(lastCommitInMainBranch.stats().additions())
                .append("\n")
                .append("Deletions : ")
                .append(lastCommitInMainBranch.stats().deletions())
                .append("\n");
        }

        return message.toString();
    }

    public GitHubLastCommitInMainBranch fetchRepoMainBranch(String user, String repository) {
        return webClient.get()
            .uri("/repos/{user}/{repository}/commits/main", user, repository)
            .retrieve()
            .bodyToMono(GitHubLastCommitInMainBranch.class)
            .block();
    }
}
