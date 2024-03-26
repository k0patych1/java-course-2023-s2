package edu.java.services.clients;

import edu.java.models.StackOverFlowLastAnswer;

public interface IStackOverFlowClient {
    StackOverFlowLastAnswer fetchQuestion(String questionId);
}
