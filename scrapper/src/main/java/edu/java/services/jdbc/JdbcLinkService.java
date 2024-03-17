package edu.java.services.jdbc;

import edu.java.models.dto.Link;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.repositories.jdbc.JdbcSubscriptionRepository;
import edu.java.services.ILinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements ILinkService {
    private final JdbcLinkRepository jdbcLinkRepository;

    private final JdbcSubscriptionRepository jdbcSubscriptionRepository;

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

        jdbcSubscriptionRepository.save(link.getId(), chatId);
    }

    @Override
    @Transactional
    public boolean remove(URI url, Long chatId) {
        Link link = jdbcLinkRepository.findByUrl(url.toString()).orElseThrow();

        boolean wasInTable = jdbcSubscriptionRepository.delete(link.getId(), chatId);

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
    public List<Link> listAll() {
        return jdbcLinkRepository.findAll();
    }

    @Override
    public List<Link> listOldChecked(OffsetDateTime interval) {
        return jdbcLinkRepository.findAllByLastCheckTimeBefore(interval);
    }


}
