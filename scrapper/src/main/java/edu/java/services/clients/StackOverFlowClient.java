package edu.java.services.clients;

import edu.java.models.StackOverFlowLastUpdate;

public interface StackOverFlowClient {
    StackOverFlowLastUpdate fetchQuestion(String questionId);
}
