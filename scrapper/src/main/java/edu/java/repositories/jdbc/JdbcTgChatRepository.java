package edu.java.repositories.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;

@RequiredArgsConstructor
public class JdbcTgChatRepository implements IJdbcTgChatRepository {
    private final JdbcClient jdbcClient;

    @Override
    public void delete(Long id) {
        jdbcClient.sql("DELETE FROM chat WHERE id = ?")
            .param(id)
            .update();
    }

    @Override
    public void save(Long id) {
        jdbcClient.sql("""
                INSERT INTO chat (id) VALUES (?)
                ON CONFLICT (id) DO NOTHING;
                """)
            .param(id)
            .update();
    }
}
