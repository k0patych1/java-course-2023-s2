package edu.java.repositories.jdbc;

import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSubscriptionRepository implements IJdbcSubscriptionRepository {
    private final JdbcClient jdbcClient;

    public JdbcSubscriptionRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void save(Long chatId, Long linkId) {
        jdbcClient.sql("""
                INSERT INTO subscriptions (chat_id, link_id, created_at)
                VALUES (?, ?, current_timestamp)
                """)
                .params(chatId, linkId)
                .update();
    }

    @Override
    public boolean delete(Long chatId, Long linkId) {
        int removed = jdbcClient.sql("DELETE FROM subscriptions WHERE chat_id = ? AND link_id = ?")
                .params(chatId, linkId)
                .update();

        return removed > 0;
    }

    @Override
    public List<Link> findAllLinksWithChatId(Long chatId) {
        return jdbcClient.sql("""
                SELECT l.id, l.url, last_check_time FROM link AS l
                JOIN subscriptions AS s ON l.id = s.link_id
                WHERE s.chat_id = ?
                """)
            .param(chatId)
            .query(Link.class)
            .list();
    }

    @Override
    public List<TgChat> findAllChatsWithLinkId(Long linkId) {
        return jdbcClient.sql("""
                SELECT chat_id as id FROM subscriptions
                WHERE link_id = ?
                """)
            .param(linkId)
            .query(TgChat.class)
            .list();
    }
}
