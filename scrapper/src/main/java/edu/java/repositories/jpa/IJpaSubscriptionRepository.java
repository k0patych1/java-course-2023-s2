package edu.java.repositories.jpa;

import edu.java.domain.jpa.Subscription;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IJpaSubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("SELECT s FROM Subscription s WHERE s.link.url = :url AND s.chat.id = :chatId")
    Optional<Subscription> findByUrlAndChatId(@Param("url") String url, @Param("chatId") Long chatId);
}
