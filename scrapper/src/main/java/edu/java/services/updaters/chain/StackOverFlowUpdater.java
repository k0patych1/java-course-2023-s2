package edu.java.services.updaters.chain;

import edu.java.models.StackOverFlowLastUpdate;
import edu.java.models.dto.Link;
import edu.java.services.clients.StackOverFlowClient;
import edu.java.services.parsers.StackOverFlowUrlParser;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StackOverFlowUpdater implements UpdatersChain {
    private final StackOverFlowClient client;

    private final StackOverFlowUrlParser urlParser;

    @Override
    public boolean canUpdate(Link link) {
        return link.getUrl().contains("stackoverflow.com");
    }

    @Override
    public int update(Link link) {
        String questionId = urlParser.getQuestionId(URI.create(link.getUrl()));

        StackOverFlowLastUpdate update = client.fetchQuestion(questionId);

        if (update.answerList().getFirst().time().isAfter(link.getLastCheckTime())) {
            return 1;
        }

        return 0;
    }
}
