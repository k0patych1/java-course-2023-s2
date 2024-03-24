package edu.java.repositories.jpa;

import edu.java.domain.jpa.Subscription;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IJpaSubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("DELETE FROM Subscription s WHERE s.chat.id = :chat_id")
    @Modifying
    void deleteByTgChatId(@Param("chat_id") Long tgChatId);

    @Query("SELECT s FROM Subscription s WHERE s.chat.id = :chat_id")
    List<Subscription> findAllByTgChatId(@Param("chat_id") Long tgChatId);

    @Query("SELECT s FROM Subscription s WHERE s.link.id = :link_id")
    List<Subscription> findAllByLinkId(@Param("link_id") Long linkId);

    @Query("SELECT s FROM Subscription s WHERE s.link.url = :url AND s.chat.id = :chatId")
    Optional<Subscription> findByUrlAndChatId(@Param("url") String url, @Param("chatId") Long chatId);
}
