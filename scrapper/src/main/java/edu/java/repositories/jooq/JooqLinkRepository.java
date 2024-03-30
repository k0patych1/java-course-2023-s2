package edu.java.repositories.jooq;

import edu.java.models.dto.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.domain.jooq.Tables.LINK;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository implements IJooqLinkRepository {
    private final DSLContext dslContext;

    @Override
    public Link save(Link link) {
        return Objects.requireNonNull(dslContext.insertInto(LINK)
                .set(LINK.URL, link.getUrl())
                .set(LINK.LAST_CHECK_TIME, link.getLastCheckTime())
                .returning()
                .fetchOne())
            .into(Link.class);
    }

    @Override
    public void delete(Long linkId) {
        dslContext.deleteFrom(LINK)
            .where(LINK.ID.eq(linkId))
            .execute();
    }

    @Override
    public void update(Long linkId, OffsetDateTime lastCheckedAt) {
        dslContext.update(LINK)
            .set(LINK.LAST_CHECK_TIME, lastCheckedAt)
            .where(LINK.ID.eq(linkId))
            .execute();
    }

    @Override
    public Optional<Link> findByUrl(String url) {
        return dslContext.selectFrom(LINK)
            .where(LINK.URL.eq(url))
            .fetchOptionalInto(Link.class);
    }

    @Override
    public List<Link> findAllByLastCheckTimeBefore(OffsetDateTime interval) {
        return dslContext.selectFrom(LINK)
            .where(LINK.LAST_CHECK_TIME.lt(interval))
            .fetchInto(Link.class);
    }
}
