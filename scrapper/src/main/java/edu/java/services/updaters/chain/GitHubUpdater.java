package edu.java.services.updaters.chain;

import edu.java.models.GitHubRepoLastUpdate;
import edu.java.models.dto.Link;
import edu.java.services.clients.GitHubClient;
import edu.java.services.parsers.GitHubUrlParser;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubUpdater implements UpdatersChain {
    private final GitHubClient client;

    private final GitHubUrlParser urlParser;

    @Override
    public boolean canUpdate(Link link) {
        return urlParser.isGitHubUrl(URI.create(link.getUrl()));
    }

    @Override
    public int update(Link link) {
        String user = urlParser.getUserName(URI.create(link.getUrl()));
        String repository = urlParser.getRepositoryName(URI.create(link.getUrl()));

        GitHubRepoLastUpdate update = client.fetchRepo(user, repository);

        if (update.time().isAfter(link.getLastCheckTime())) {
            return 1;
        }

        return 0;
    }
}
