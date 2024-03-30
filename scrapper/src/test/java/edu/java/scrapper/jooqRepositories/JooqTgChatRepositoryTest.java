package edu.java.scrapper.jooqRepositories;

import edu.java.repositories.jooq.IJooqTgChatRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class JooqTgChatRepositoryTest extends IntegrationTest {
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jooq");
    }

    @Autowired
    private IJooqTgChatRepository jooqTgChatRepository;

    @Test
    @Transactional
    @Rollback
    public void saveTest() {
        assertDoesNotThrow(() -> jooqTgChatRepository.save(1L));
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        assertDoesNotThrow(() -> jooqTgChatRepository.save(1L));
        assertDoesNotThrow(() -> jooqTgChatRepository.delete(1L));
    }
}
