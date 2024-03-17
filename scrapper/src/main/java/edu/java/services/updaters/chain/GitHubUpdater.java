package edu.java.services.updaters.chain;

import edu.java.models.GitHubRepoLastUpdate;
import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.models.dto.request.LinkUpdate;
import edu.java.services.clients.BotClient;
import edu.java.services.clients.GitHubClient;
import edu.java.services.parsers.GitHubUrlParser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubUpdater implements UpdatersChain {
    private final BotClient botClient;

    private final GitHubClient gitHubClient;

    private final GitHubUrlParser urlParser;

    @Override
    public boolean canUpdate(Link link) {
        return urlParser.isGitHubUrl(link.getUrl());
    }

    @Override
    public int update(Link link, List<TgChat> tgChats) {
        GitHubRepoLastUpdate update = gitHubClient.fetchRepo(link);

        if (update.time().isAfter(link.getLastCheckTime())) {
            LinkUpdate updateToBot = new LinkUpdate();
            updateToBot.setId(updateToBot.getId());
            updateToBot.setUrl(updateToBot.getUrl());
            updateToBot.setTgChatIds(tgChats.stream().map(TgChat::getId).toList());
            updateToBot.setDescription(gitHubClient.getInfoAboutUpdate(link));
            botClient.update(updateToBot);
            return 1;
        }

        return 0;
    }
}
