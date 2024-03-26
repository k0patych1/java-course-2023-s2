package edu.java.services;

import edu.java.models.GitHubLastCommitInMainBranch;
import edu.java.models.GitHubRepoLastUpdate;
import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.models.dto.request.LinkUpdate;
import edu.java.services.clients.IGitHubClient;
import edu.java.services.parsers.GitHubUrlParser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubService implements IGitHubService {
    private final IGitHubClient gitHubClient;

    private final GitHubUrlParser urlParser;

    @Override
    public LinkUpdate formUpdate(GitHubRepoLastUpdate lastUpdate, Link link, List<TgChat> tgChats) {
        LinkUpdate updateToBot = new LinkUpdate();
        updateToBot.setId(updateToBot.getId());
        updateToBot.setUrl(updateToBot.getUrl());
        updateToBot.setTgChatIds(tgChats.stream().map(TgChat::getId).toList());

        String user = urlParser.getUserName(link.getUrl());
        String repository = urlParser.getRepositoryName(link.getUrl());

        GitHubLastCommitInMainBranch lastCommitInMainBranch = gitHubClient.fetchRepoMainBranch(user, repository);

        if (lastCommitInMainBranch.commit().committer().time().isAfter(link.getLastCheckTime())) {
            updateToBot.setDescription(formMessageAboutNewCommit(lastCommitInMainBranch, link));
        } else {
            updateToBot.setDescription(formDefaultUpdateMessage(link));
        }

        return updateToBot;
    }

    private String formMessageAboutNewCommit(GitHubLastCommitInMainBranch commit, Link link) {
        return new StringBuilder()
            .append(formDefaultUpdateMessage(link))
            .append("New commit in main branch:")
            .append("\n")
            .append("Name: ")
            .append(commit.commit().message())
            .append("\n")
            .append("Committer: ")
            .append(commit.commit().committer().name())
            .append("\n")
            .append("Time : ")
            .append(commit.commit().committer().time())
            .append("\n")
            .append("Additions : ")
            .append(commit.stats().additions())
            .append("\n")
            .append("Deletions : ")
            .append(commit.stats().deletions())
            .append("\n")
            .toString();
    }

    private String formDefaultUpdateMessage(Link link) {
        return "Repository " + link.getUrl() + " was updated\n";
    }
}
