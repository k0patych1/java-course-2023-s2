package edu.java.services;

import edu.java.models.dto.TgChat;
import java.util.List;

public interface ITgChatService {
    void register(Long tgChatId);

    void unregister(Long tgChatId);

    List<TgChat> listAllWithLink(Long linkId);
}
