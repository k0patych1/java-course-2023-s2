package edu.java.repositories.jdbc;

import edu.java.models.dto.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface IJdbcLinkRepository {
    Link save(Link link);

    void delete(Long linkId);

    void update(Long linkId, OffsetDateTime lastCheckedAt);

    Optional<Link> findByUrl(String url);

    List<Link> findAllByLastCheckTimeBefore(OffsetDateTime interval);
}
