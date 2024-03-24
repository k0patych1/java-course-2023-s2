package edu.java.repositories.jpa;

import edu.java.domain.jpa.TgChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJpaTgChatRepository extends JpaRepository<TgChat, Long> {

}
