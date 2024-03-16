package edu.java.services.clients;

import edu.java.models.StackOverFlowLastUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StackOverFlowClient implements IStackOverFlowClient {
    private final WebClient webClient;

    @Autowired
    public StackOverFlowClient(@Qualifier("stackOverflowWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public StackOverFlowLastUpdate fetchQuestion(String questionId) {
        return webClient
            .get()
            .uri("/questions/{questionId}?order=desc&sort=activity&site=stackoverflow",
                questionId)
            .retrieve()
            .bodyToMono(StackOverFlowLastUpdate.class)
            .block();
    }
}
