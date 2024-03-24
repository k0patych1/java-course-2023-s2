package edu.java.services.jooq;

import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.repositories.jooq.IJooqLinkRepository;
import edu.java.repositories.jooq.IJooqSubscriptionRepository;
import edu.java.repositories.jooq.IJooqTgChatRepository;
import edu.java.services.ITgChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JooqTgChatService implements ITgChatService {
    private final IJooqTgChatRepository jooqTgChatRepository;

    private final IJooqLinkRepository jooqLinkRepository;

    private final IJooqSubscriptionRepository jooqSubscriptionRepository;

    @Override
    public void register(Long tgChatId) {
        jooqTgChatRepository.save(tgChatId);
    }

    @Override
    public void unregister(Long tgChatId) {
        List<Link> trackedLinks = jooqSubscriptionRepository.findAllLinksWithChatId(tgChatId);
        trackedLinks.forEach(link -> {
            jooqSubscriptionRepository.delete(tgChatId, link.getId());
            if (jooqSubscriptionRepository.findAllChatsWithLinkId(link.getId()).isEmpty()) {
                jooqLinkRepository.delete(link.getId());
            }
        });
        jooqTgChatRepository.delete(tgChatId);
    }

    @Override
    public List<TgChat> listAllWithLink(Long linkId) {
        return jooqSubscriptionRepository.findAllChatsWithLinkId(linkId);
    }
}
