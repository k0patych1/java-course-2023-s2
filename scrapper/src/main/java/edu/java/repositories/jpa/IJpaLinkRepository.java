package edu.java.repositories.jpa;

import edu.java.domain.jpa.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IJpaLinkRepository extends JpaRepository<Link, Long> {
    @Query("SELECT l FROM Link l WHERE l.url = :url")
    Optional<Link> findByUrl(@Param("url") String url);

    @Query("SELECT l FROM Link l WHERE l.lastCheckTime < :time")
    List<Link> findAllByLastCheckTimeBefore(@Param("time") OffsetDateTime interval);
}
