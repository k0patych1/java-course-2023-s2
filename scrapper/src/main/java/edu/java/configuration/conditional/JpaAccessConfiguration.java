package edu.java.configuration.conditional;

import edu.java.repositories.jpa.IJpaLinkRepository;
import edu.java.repositories.jpa.IJpaSubscriptionRepository;
import edu.java.repositories.jpa.IJpaTgChatRepository;
import edu.java.services.jpa.JpaLinkService;
import edu.java.services.jpa.JpaTgChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public JpaLinkService jpaLinkService(
        IJpaTgChatRepository jpaTgChatRepository,
        IJpaLinkRepository jpaLinkRepository,
        IJpaSubscriptionRepository jpaSubscriptionRepository
    ) {
        return new JpaLinkService(jpaTgChatRepository, jpaLinkRepository, jpaSubscriptionRepository);
    }

    @Bean
    public JpaTgChatService jpaTgChatService(
        IJpaTgChatRepository jpaTgChatRepository,
        IJpaLinkRepository jpaLinkRepository
    ) {
        return new JpaTgChatService(jpaTgChatRepository, jpaLinkRepository);
    }
}
