package edu.java.scrapper.jdbcRepositories;

import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.repositories.jdbc.JdbcSubscriptionRepository;
import edu.java.repositories.jdbc.JdbcTgChatRepository;
import java.time.OffsetDateTime;
import java.util.List;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JdbcSubscriptionsTest extends IntegrationTest {
    @Autowired
    private JdbcSubscriptionRepository subscriptionRepository;

    @Autowired
    private JdbcTgChatRepository chatRepository;

    @Autowired
    private JdbcLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void incorrectSaveTest() {
        assertThatThrownBy(() -> subscriptionRepository.save(1L, 1L))
                .isInstanceOf(Exception.class);
    }

    @Test
    @Transactional
    @Rollback
    public void saveAndFindAllChatsWithLinkIdAndFindAllLinksWithChatIdTest() {
        chatRepository.save(1L);
        chatRepository.save(2L);

        Link link1 = linkRepository.save(new Link(666L, "https://www.google.com",  OffsetDateTime.now()));
        Link link2 = linkRepository.save(new Link(666L, "https://github.com",  OffsetDateTime.now()));

        subscriptionRepository.save(1L, link1.getId());
        subscriptionRepository.save(1L, link2.getId());
        subscriptionRepository.save(2L, link2.getId());

        List<TgChat> chats = subscriptionRepository.findAllChatsWithLinkId(link2.getId());
        assertThat(chats.size()).isEqualTo(2);
        assertThat(chats.get(0).getId()).isEqualTo(1L);
        assertThat(chats.get(1).getId()).isEqualTo(2L);

        List<Link> links = subscriptionRepository.findAllLinksWithChatId(1L);
        assertThat(links.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        chatRepository.save(1L);

        Link link = linkRepository.save(new Link(666L, "https://www.google.com",  OffsetDateTime.now()));

        subscriptionRepository.save(1L, link.getId());

        subscriptionRepository.delete(1L, link.getId());

        List<TgChat> chats = subscriptionRepository.findAllChatsWithLinkId(link.getId());
        assertTrue(chats.isEmpty());

        List<Link> links = subscriptionRepository.findAllLinksWithChatId(1L);
        assertTrue(links.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void cascadeDeleteOnTgChatTest() {
        chatRepository.save(1L);

        Link link = linkRepository.save(new Link(666L, "https://www.google.com",  OffsetDateTime.now()));

        subscriptionRepository.save(1L, link.getId());

        chatRepository.delete(1L);

        List<TgChat> chats = subscriptionRepository.findAllChatsWithLinkId(link.getId());
        assertTrue(chats.isEmpty());

        List<Link> links = subscriptionRepository.findAllLinksWithChatId(1L);
        assertTrue(links.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void cascadeDeleteOnLinkTest() {
        chatRepository.save(1L);

        Link link = linkRepository.save(new Link(666L, "https://www.google.com",  OffsetDateTime.now()));

        subscriptionRepository.save(1L, link.getId());

        linkRepository.delete(link.getId());

        List<TgChat> chats = subscriptionRepository.findAllChatsWithLinkId(link.getId());
        assertTrue(chats.isEmpty());

        List<Link> links = subscriptionRepository.findAllLinksWithChatId(1L);
        assertTrue(links.isEmpty());
    }
}
