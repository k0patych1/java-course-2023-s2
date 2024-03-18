package edu.java.repositories.jdbc;

import edu.java.models.dto.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLinkRepository implements IJdbcLinkRepository {
    private final JdbcClient jdbcClient;

    public JdbcLinkRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Link save(Link link) {
        return jdbcClient.sql("INSERT INTO link (url, last_check_time) VALUES (?, ?) RETURNING *")
            .params(link.getUrl(), link.getLastCheckTime())
            .query(Link.class)
            .single();
    }

    @Override
    public void delete(Long linkId) {
        jdbcClient.sql("DELETE FROM link WHERE id = ?")
            .param(linkId)
            .update();
    }

    @Override
    public void update(Long linkId, OffsetDateTime lastCheckedAt) {
        jdbcClient.sql("UPDATE link SET last_check_time = ? WHERE id = ?")
            .params(lastCheckedAt, linkId)
            .update();
    }

    @Override
    public Optional<Link> findByUrl(String url) {
        return jdbcClient.sql("SELECT * FROM link WHERE url = ?")
            .param(url)
            .query(Link.class)
            .optional();
    }

    @Override
    public List<Link> findAllByLastCheckTimeBefore(OffsetDateTime interval) {
        return jdbcClient.sql("SELECT * FROM link WHERE last_check_time < ?")
            .param(interval)
            .query(Link.class)
            .list();
    }
}
