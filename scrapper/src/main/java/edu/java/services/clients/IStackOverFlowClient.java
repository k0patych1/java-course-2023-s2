package edu.java.services.clients;

import edu.java.models.StackOverFlowLastUpdate;

public interface IStackOverFlowClient {
    StackOverFlowLastUpdate fetchQuestion(String questionId);
}
