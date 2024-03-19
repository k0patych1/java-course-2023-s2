package edu.java.services;

import edu.java.models.StackOverFlowLastAnswer;
import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.models.dto.request.LinkUpdate;
import java.util.List;

public interface IStackOverFlowService {
    LinkUpdate formUpdate(StackOverFlowLastAnswer answer, Link link, List<TgChat> tgChats);
}
