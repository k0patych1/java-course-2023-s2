package edu.java.scrapper.jpaRepositories;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.Subscription;
import edu.java.domain.jpa.TgChat;
import edu.java.repositories.jpa.IJpaLinkRepository;
import edu.java.repositories.jpa.IJpaSubscriptionRepository;
import java.time.OffsetDateTime;
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
public class JpaSubscriptionTest extends IntegrationTest {
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
    }

    @Autowired
    private IJpaSubscriptionRepository subscriptionRepository;

    @Autowired
    private IJpaLinkRepository jpaLinkRepository;

    @Autowired
    private IJpaTgChatRepository jpaTgChatRepository;

    @Test
    @Transactional
    @Rollback
    public void deleteByTgChatIdTest() {
        TgChat tgChat = new TgChat();
        tgChat.setId(1L);

        jpaTgChatRepository.save(tgChat);

        Link link = new Link();
        link.setUrl("test");
        link.setLastCheckTime(OffsetDateTime.now());

        jpaLinkRepository.save(link);

        Subscription subscription = new Subscription();
        subscription.setChat(tgChat);
        subscription.setCreatedAt(OffsetDateTime.now());
        subscription.setLink(link);

        subscriptionRepository.save(subscription);

        subscriptionRepository.deleteByTgChatId(tgChat.getId());

        assertThat(subscriptionRepository.findAll().size())
            .isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllByTgChatIdTest() {
        TgChat tgChat = new TgChat();
        tgChat.setId(1L);

        jpaTgChatRepository.save(tgChat);

        Link link = new Link();
        link.setUrl("test");
        link.setLastCheckTime(OffsetDateTime.now());

        jpaLinkRepository.save(link);

        Subscription subscription = new Subscription();
        subscription.setChat(tgChat);
        subscription.setCreatedAt(OffsetDateTime.now());
        subscription.setLink(link);

        subscriptionRepository.save(subscription);

        assertThat(subscriptionRepository.findAllByTgChatId(tgChat.getId()).size())
            .isEqualTo(1);
    }
}
