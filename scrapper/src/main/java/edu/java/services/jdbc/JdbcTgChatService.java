package edu.java.services.jdbc;

import edu.java.models.dto.TgChat;
import edu.java.repositories.jdbc.IJdbcSubscriptionRepository;
import edu.java.repositories.jdbc.IJdbcTgChatRepository;
import edu.java.services.ITgChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements ITgChatService {
    private final IJdbcTgChatRepository jdbcTgChatRepository;

    private final IJdbcSubscriptionRepository jdbcSubscriptionRepository;

    @Override
    public void register(Long id) {
        jdbcTgChatRepository.save(id);
    }

    @Override
    public void unregister(Long id) {
        jdbcTgChatRepository.delete(id);
    }

    @Override
    public List<TgChat> listAllWithLink(Long linkId) {
        return jdbcSubscriptionRepository.findAllChatsWithLinkId(linkId);
    }
}
