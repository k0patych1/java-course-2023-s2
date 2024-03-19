package edu.java.services.jdbc;

import edu.java.models.dto.Link;
import edu.java.repositories.jdbc.IJdbcLinkRepository;
import edu.java.repositories.jdbc.IJdbcSubscriptionRepository;
import edu.java.services.ILinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements ILinkService {
    private final IJdbcLinkRepository jdbcLinkRepository;

    private final IJdbcSubscriptionRepository jdbcSubscriptionRepository;

    @Override
    @Transactional
    public void add(URI url, Long chatId) {
        Optional<Link> linkOptional = jdbcLinkRepository.findByUrl(url.toString());
        Link link;
        if (linkOptional.isEmpty()) {
            link = new Link(0L, url.toString(), OffsetDateTime.now());
            link = jdbcLinkRepository.save(link);
        } else {
            link = linkOptional.get();
        }

        jdbcSubscriptionRepository.save(chatId, link.getId());
    }

    @Override
    @Transactional
    public boolean remove(URI url, Long chatId) {
        Link link = jdbcLinkRepository.findByUrl(url.toString()).orElseThrow();

        boolean wasInTable = jdbcSubscriptionRepository.delete(chatId, link.getId());

        if (wasInTable && jdbcSubscriptionRepository.findAllChatsWithLinkId(link.getId()).isEmpty()) {
            jdbcLinkRepository.delete(link.getId());
        }

        return wasInTable;
    }

    @Override
    public void update(Long linkId, OffsetDateTime lastCheckedAt) {
        jdbcLinkRepository.update(linkId, lastCheckedAt);
    }

    @Override
    public List<Link> listAllWithChatId(Long chatId) {
        return jdbcSubscriptionRepository.findAllLinksWithChatId(chatId);
    }

    @Override
    public List<Link> listOldChecked(OffsetDateTime interval) {
        return jdbcLinkRepository.findAllByLastCheckTimeBefore(interval);
    }
}
