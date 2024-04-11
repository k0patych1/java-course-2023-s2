package edu.java.services.updaters.chain;

import edu.java.models.StackOverFlowLastAnswer;
import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.services.IStackOverFlowService;
import edu.java.services.clients.IStackOverFlowClient;
import edu.java.services.notification.NotificationService;
import edu.java.services.parsers.StackOverFlowUrlParser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StackOverFlowUpdater implements UpdatersChain {
    private final NotificationService notificationService;

    private final IStackOverFlowClient stackOverFlowClient;

    private final StackOverFlowUrlParser urlParser;

    private final IStackOverFlowService stackOverFlowService;


    @Override
    public boolean canUpdate(Link link) {
        return urlParser.isStackOverFlowUrl(link.getUrl());
    }

    @Override
    public int update(Link link, List<TgChat> tgChats) {
        String questionId = urlParser.getQuestionId(link.getUrl());

        StackOverFlowLastAnswer answer = stackOverFlowClient.fetchQuestion(questionId);

        if (!answer.answerList().isEmpty() && answer.answerList().getFirst().time().isAfter(link.getLastCheckTime())) {
            notificationService.update(stackOverFlowService.formUpdate(answer, link, tgChats));
            return 1;
        }

        return 0;
    }
}
