package edu.java.repositories.jooq;

import edu.java.domain.jooq.tables.records.LinkRecord;
import edu.java.models.dto.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface IJooqLinkRepository {
    Link save(Link link);

    void delete(Long linkId);

    void update(Long linkId, OffsetDateTime lastCheckedAt);

    Optional<Link> findByUrl(String url);

    List<Link> findAllByLastCheckTimeBefore(OffsetDateTime interval);
}
