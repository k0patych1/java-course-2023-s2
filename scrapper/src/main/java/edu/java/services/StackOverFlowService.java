package edu.java.services;

import edu.java.models.StackOverFlowLastAnswer;
import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.models.dto.request.LinkUpdate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StackOverFlowService implements IStackOverFlowService {
    @Override
    public LinkUpdate formUpdate(StackOverFlowLastAnswer answer, Link link, List<TgChat> tgChats) {
        LinkUpdate updateToBot = new LinkUpdate();
        updateToBot.setId(updateToBot.getId());
        updateToBot.setUrl(updateToBot.getUrl());
        updateToBot.setTgChatIds(tgChats.stream().map(TgChat::getId).toList());

        String description = new StringBuilder()
            .append("In question ")
            .append(link.getUrl())
            .append(" new answer from ")
            .append(answer.answerList().getFirst().owner().displayName())
            .append(" with score ")
            .append(answer.answerList().getFirst().score())
            .append('\n')
            .toString();

        updateToBot.setDescription(description);

        return updateToBot;
    }
}
