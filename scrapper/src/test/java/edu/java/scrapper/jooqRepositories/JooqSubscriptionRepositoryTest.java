package edu.java.scrapper.jooqRepositories;

import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.repositories.jooq.IJooqLinkRepository;
import edu.java.repositories.jooq.IJooqSubscriptionRepository;
import edu.java.repositories.jooq.IJooqTgChatRepository;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JooqSubscriptionRepositoryTest extends IntegrationTest {
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jooq");
    }

    @Autowired
    private IJooqLinkRepository jooqLinkRepository;

    @Autowired
    private IJooqTgChatRepository jooqTgChatRepository;

    @Autowired
    private IJooqSubscriptionRepository jooqSubscriptionRepository;

    @Test
    @Transactional
    @Rollback
    public void incorrectSaveTest() {
        assertThatThrownBy(() -> jooqSubscriptionRepository.save(1L, 1L))
            .isInstanceOf(Exception.class);
    }

    @Test
    @Transactional
    @Rollback
    public void saveAndFindAllChatsWithLinkIdTest() {
        jooqTgChatRepository.save(1L);
        jooqTgChatRepository.save(2L);

        Link link1 = jooqLinkRepository.save(new Link(666L, "https://www.google.com", OffsetDateTime.now()));
        Link link2 = jooqLinkRepository.save(new Link(666L, "https://github.com", OffsetDateTime.now()));

        jooqSubscriptionRepository.save(1L, link1.getId());
        jooqSubscriptionRepository.save(1L, link2.getId());
        jooqSubscriptionRepository.save(2L, link2.getId());

        List<TgChat> chats = jooqSubscriptionRepository.findAllChatsWithLinkId(link2.getId());
        assertThat(chats.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        jooqTgChatRepository.save(1L);

        Link link = jooqLinkRepository.save(new Link(666L, "https://www.google.com", OffsetDateTime.now()));

        jooqSubscriptionRepository.save(1L, link.getId());

        jooqSubscriptionRepository.delete(1L, link.getId());

        List<TgChat> chats = jooqSubscriptionRepository.findAllChatsWithLinkId(link.getId());
        assertTrue(chats.isEmpty());

        List<Link> links = jooqSubscriptionRepository.findAllLinksWithChatId(1L);
        assertTrue(links.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void cascadeDeleteOnTgChatTest() {
        jooqTgChatRepository.save(1L);

        Link link = jooqLinkRepository.save(new Link(666L, "https://www.google.com", OffsetDateTime.now()));

        jooqSubscriptionRepository.save(1L, link.getId());

        jooqTgChatRepository.delete(1L);

        List<TgChat> chats = jooqSubscriptionRepository.findAllChatsWithLinkId(link.getId());
        assertTrue(chats.isEmpty());

        List<Link> links = jooqSubscriptionRepository.findAllLinksWithChatId(1L);
        assertTrue(links.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void cascadeDeleteOnLinkTest() {
        jooqTgChatRepository.save(1L);

        Link link = jooqLinkRepository.save(new Link(666L, "https://www.google.com", OffsetDateTime.now()));

        jooqSubscriptionRepository.save(1L, link.getId());

        jooqLinkRepository.delete(link.getId());

        List<TgChat> chats = jooqSubscriptionRepository.findAllChatsWithLinkId(link.getId());
        assertTrue(chats.isEmpty());

        List<Link> links = jooqSubscriptionRepository.findAllLinksWithChatId(1L);
        assertTrue(links.isEmpty());
    }
}
