package edu.java.services.updaters.chain;

import edu.java.models.GitHubRepoLastUpdate;
import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.services.IGitHubService;
import edu.java.services.clients.IBotClient;
import edu.java.services.clients.IGitHubClient;
import edu.java.services.parsers.GitHubUrlParser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubUpdater implements UpdatersChain {
    private final IBotClient botClient;

    private final IGitHubClient gitHubClient;

    private final IGitHubService gitHubService;

    private final GitHubUrlParser urlParser;

    @Override
    public boolean canUpdate(Link link) {
        return urlParser.isGitHubUrl(link.getUrl());
    }

    @Override
    public int update(Link link, List<TgChat> tgChats) {
        String user = urlParser.getUserName(link.getUrl());
        String repository = urlParser.getRepositoryName(link.getUrl());

        GitHubRepoLastUpdate update = gitHubClient.fetchRepo(user, repository);

        if (update.time().isAfter(link.getLastCheckTime())) {
            botClient.update(gitHubService.formUpdate(update, link, tgChats));
            return 1;
        }

        return 0;
    }
}
