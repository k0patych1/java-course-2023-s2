package edu.java.repositories.jdbc;

import edu.java.repositories.ITgChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTgChatRepository implements ITgChatRepository {
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
        jdbcClient.sql("INSERT INTO chat (id) VALUES (?)")
            .param(id)
            .update();
    }
}
