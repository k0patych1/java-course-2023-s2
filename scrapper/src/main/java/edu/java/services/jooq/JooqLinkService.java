package edu.java.services.jooq;

import edu.java.models.dto.Link;
import edu.java.repositories.jooq.IJooqLinkRepository;
import edu.java.repositories.jooq.IJooqSubscriptionRepository;
import edu.java.services.ILinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JooqLinkService implements ILinkService {
    private final IJooqLinkRepository jooqLinkRepository;

    private final IJooqSubscriptionRepository jooqSubscriptionRepository;

    @Override
    @Transactional
    public void add(URI url, Long chatId) {
        Optional<Link> linkOptional = jooqLinkRepository.findByUrl(url.toString());
        Link link;

        if (linkOptional.isEmpty()) {
            link = new Link(0L, url.toString(), OffsetDateTime.now());
            link = jooqLinkRepository.save(link);
        } else {
            link = linkOptional.get();
        }

        jooqSubscriptionRepository.save(chatId, link.getId());
    }

    @Override
    @Transactional
    public boolean remove(URI url, Long chatId) {
        Link link = jooqLinkRepository.findByUrl(url.toString()).orElseThrow();

        boolean wasInTable = jooqSubscriptionRepository.delete(chatId, link.getId());

        if (wasInTable && jooqSubscriptionRepository.findAllChatsWithLinkId(link.getId()).isEmpty()) {
            jooqLinkRepository.delete(link.getId());
        }

        return wasInTable;
    }

    @Override
    public void update(Long linkId, OffsetDateTime lastCheckedAt) {
        jooqLinkRepository.update(linkId, lastCheckedAt);
    }

    @Override
    public List<Link> listAllWithChatId(Long chatId) {
        return jooqSubscriptionRepository.findAllLinksWithChatId(chatId);
    }

    @Override
    public List<Link> listOldChecked(OffsetDateTime interval) {
        return jooqLinkRepository.findAllByLastCheckTimeBefore(interval);
    }
}
