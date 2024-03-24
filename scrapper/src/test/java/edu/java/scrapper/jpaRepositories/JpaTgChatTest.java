package edu.java.scrapper.jpaRepositories;

import edu.java.domain.jpa.TgChat;
import edu.java.repositories.jpa.IJpaTgChatRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JpaTgChatTest extends IntegrationTest {
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
    }

    @Autowired
    private IJpaTgChatRepository tgChatRepository;

    @Test
    @Transactional
    @Rollback
    public void saveTest() {
        TgChat tgChat = new TgChat();
        tgChat.setId(1111L);
        TgChat savedTgChat = tgChatRepository.save(tgChat);
        assertThat(tgChat).isEqualTo(savedTgChat);
    }
}
