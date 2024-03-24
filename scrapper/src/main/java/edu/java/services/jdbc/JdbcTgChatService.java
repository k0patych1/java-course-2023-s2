package edu.java.services.jdbc;

import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.repositories.jdbc.IJdbcLinkRepository;
import edu.java.repositories.jdbc.IJdbcSubscriptionRepository;
import edu.java.repositories.jdbc.IJdbcTgChatRepository;
import edu.java.services.ITgChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements ITgChatService {
    private final IJdbcTgChatRepository jdbcTgChatRepository;

    private final IJdbcLinkRepository jdbcLinkRepository;

    private final IJdbcSubscriptionRepository jdbcSubscriptionRepository;

    @Override
    public void register(Long id) {
        jdbcTgChatRepository.save(id);
    }

    @Override
    @Transactional
    public void unregister(Long id) {
        List<Link> trackedLinks = jdbcSubscriptionRepository.findAllLinksWithChatId(id);
        trackedLinks.forEach(link -> {
            jdbcSubscriptionRepository.delete(id, link.getId());
            if (jdbcSubscriptionRepository.findAllChatsWithLinkId(link.getId()).isEmpty()) {
                jdbcLinkRepository.delete(link.getId());
            }
        });
        jdbcTgChatRepository.delete(id);
    }

    @Override
    public List<TgChat> listAllWithLink(Long linkId) {
        return jdbcSubscriptionRepository.findAllChatsWithLinkId(linkId);
    }
}
