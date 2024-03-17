package edu.java.repositories;

import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import java.util.List;

public interface ISubscriptionRepository {
    void save(Long chatId, Long linkId);

    boolean delete(Long chatId, Long linkId);

    List<Link> findAllLinksWithChatId(Long chatId);

    List<TgChat> findAllChatsWithLinkId(Long linkId);
}
