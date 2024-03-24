package edu.java.scrapper.jdbcRepositories;

import edu.java.models.dto.TgChat;
import edu.java.repositories.jdbc.IJdbcTgChatRepository;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JdbcTgChatTest extends IntegrationTest {
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jdbc");
    }
    @Autowired
    private IJdbcTgChatRepository chatService;

    @Autowired
    private JdbcClient jdbcClient;

    @Test
    @Transactional
    @Rollback
    public void saveTest() {
        chatService.save(1L);
        TgChat chat = jdbcClient.sql("SELECT * FROM chat WHERE id = 1").query(TgChat.class).single();
        assertThat(1L).isEqualTo(chat.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        chatService.save(1L);
        chatService.delete(1L);
        List<TgChat> chats = jdbcClient.sql("SELECT * FROM chat").query(TgChat.class).list();
        assertTrue(chats.isEmpty());
    }
}
