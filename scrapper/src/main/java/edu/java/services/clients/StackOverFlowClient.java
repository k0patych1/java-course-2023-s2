package edu.java.services.clients;

import edu.java.models.StackOverFlowLastAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
public class StackOverFlowClient implements IStackOverFlowClient {
    private final WebClient stackOverflowWebClient;

    private final Retry retry;

    @Override
    public StackOverFlowLastAnswer fetchQuestion(String questionId) {
        return stackOverflowWebClient
            .get()
            .uri("/questions/{questionId}/answers?order=desc&sort=activity&site=stackoverflow&filter=withbody",
                questionId)
            .retrieve()
            .bodyToMono(StackOverFlowLastAnswer.class)
            .retryWhen(retry)
            .block();
    }
}
