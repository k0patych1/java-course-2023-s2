package edu.java.services.clients;

import edu.java.models.StackOverFlowLastUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StackOverFlowClientImpl implements StackOverFlowClient {
    private final WebClient webClient;

    @Autowired
    public StackOverFlowClientImpl(@Qualifier("stackOverflowClient") WebClient webClient) {
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
