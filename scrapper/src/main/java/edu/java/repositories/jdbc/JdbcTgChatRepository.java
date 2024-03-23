package edu.java.repositories.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTgChatRepository implements IJdbcTgChatRepository {
    private final JdbcClient jdbcClient;

    @Autowired
    public JdbcTgChatRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

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
