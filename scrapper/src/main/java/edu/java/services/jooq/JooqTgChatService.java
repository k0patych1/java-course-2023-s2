package edu.java.services.jooq;

import edu.java.models.dto.TgChat;
import edu.java.repositories.jooq.IJooqSubscriptionRepository;
import edu.java.repositories.jooq.IJooqTgChatRepository;
import edu.java.services.ITgChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JooqTgChatService implements ITgChatService {
    private final IJooqTgChatRepository jooqTgChatRepository;

    private final IJooqSubscriptionRepository jooqSubscriptionRepository;

    @Override
    public void register(Long tgChatId) {
        jooqTgChatRepository.save(tgChatId);
    }

    @Override
    public void unregister(Long tgChatId) {
        jooqTgChatRepository.delete(tgChatId);
    }

    @Override
    public List<TgChat> listAllWithLink(Long linkId) {
        return jooqSubscriptionRepository.findAllChatsWithLinkId(linkId);
    }
}
