package edu.java.configuration.conditional;

import edu.java.repositories.jooq.IJooqLinkRepository;
import edu.java.repositories.jooq.IJooqSubscriptionRepository;
import edu.java.repositories.jooq.IJooqTgChatRepository;
import edu.java.repositories.jooq.JooqLinkRepository;
import edu.java.repositories.jooq.JooqSubscriptionRepository;
import edu.java.repositories.jooq.JooqTgChatRepository;
import edu.java.services.jooq.JooqLinkService;
import edu.java.services.jooq.JooqTgChatService;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqAccessConfiguration {
    private final DSLContext dslContext;

    @Bean
    public IJooqLinkRepository jooqLinkRepository() {
        return new JooqLinkRepository(dslContext);
    }

    @Bean
    public IJooqTgChatRepository jooqTgChatRepository() {
        return new JooqTgChatRepository(dslContext);
    }

    @Bean
    public IJooqSubscriptionRepository jooqSubscriptionRepository() {
        return new JooqSubscriptionRepository(dslContext);
    }

    @Bean
    public JooqLinkService jooqLinkService(
        IJooqLinkRepository jooqLinkRepository,
        IJooqSubscriptionRepository jooqSubscriptionRepository
    ) {
        return new JooqLinkService(jooqLinkRepository, jooqSubscriptionRepository);
    }

    @Bean
    public JooqTgChatService jooqTgChatService(
        IJooqTgChatRepository jooqTgChatRepository,
        IJooqLinkRepository jooqLinkRepository,
        IJooqSubscriptionRepository jooqSubscriptionRepository
    ) {
        return new JooqTgChatService(jooqTgChatRepository, jooqLinkRepository, jooqSubscriptionRepository);
    }
}
