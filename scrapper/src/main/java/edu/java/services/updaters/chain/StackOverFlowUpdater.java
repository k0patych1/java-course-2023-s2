package edu.java.services.updaters.chain;

import edu.java.models.StackOverFlowLastUpdate;
import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.models.dto.request.LinkUpdate;
import edu.java.services.clients.BotClient;
import edu.java.services.clients.StackOverFlowClient;
import edu.java.services.parsers.StackOverFlowUrlParser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StackOverFlowUpdater implements UpdatersChain {
    private final BotClient botClient;

    private final StackOverFlowClient client;

    private final StackOverFlowUrlParser urlParser;

    @Override
    public boolean canUpdate(Link link) {
        return urlParser.isStackOverFlowUrl(link.getUrl());
    }

    @Override
    public int update(Link link, List<TgChat> tgChats) {
        String questionId = urlParser.getQuestionId(link.getUrl());

        StackOverFlowLastUpdate update = client.fetchQuestion(questionId);

        if (update.answerList().getFirst().time().isAfter(link.getLastCheckTime())) {
            LinkUpdate updateToBot = new LinkUpdate();
            updateToBot.setId(updateToBot.getId());
            updateToBot.setUrl(updateToBot.getUrl());
            updateToBot.setTgChatIds(tgChats.stream().map(TgChat::getId).toList());
            updateToBot.setDescription("GitHub repository was updated");
            botClient.update(updateToBot);
            return 1;
        }

        return 0;
    }
}
