package edu.java.configuration.conditional;

import edu.java.repositories.jdbc.IJdbcLinkRepository;
import edu.java.repositories.jdbc.IJdbcSubscriptionRepository;
import edu.java.repositories.jdbc.IJdbcTgChatRepository;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.repositories.jdbc.JdbcSubscriptionRepository;
import edu.java.repositories.jdbc.JdbcTgChatRepository;
import edu.java.services.jdbc.JdbcLinkService;
import edu.java.services.jdbc.JdbcTgChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration {
    private final JdbcClient jdbcClient;

    @Bean
    public IJdbcLinkRepository jdbcLinkRepository() {
        return new JdbcLinkRepository(jdbcClient);
    }

    @Bean
    public IJdbcTgChatRepository jdbcTgChatRepository() {
        return new JdbcTgChatRepository(jdbcClient);
    }

    @Bean
    public IJdbcSubscriptionRepository jdbcSubscriptionRepository() {
        return new JdbcSubscriptionRepository(jdbcClient);
    }

    @Bean
    public JdbcLinkService jdbcLinkService(
        IJdbcLinkRepository jdbcLinkRepository,
        IJdbcSubscriptionRepository jdbcSubscriptionRepository
    ) {
        return new JdbcLinkService(jdbcLinkRepository, jdbcSubscriptionRepository);
    }

    @Bean
    public JdbcTgChatService jdbcTgChatService(
        IJdbcTgChatRepository jdbcTgChatRepository,
        IJdbcLinkRepository jdbcLinkRepository,
        IJdbcSubscriptionRepository jdbcSubscriptionRepository
    ) {
        return new JdbcTgChatService(jdbcTgChatRepository, jdbcLinkRepository, jdbcSubscriptionRepository);
    }
}
