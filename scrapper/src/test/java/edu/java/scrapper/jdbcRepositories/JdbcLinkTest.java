package edu.java.scrapper.jdbcRepositories;

import edu.java.models.dto.Link;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JdbcLinkTest extends IntegrationTest {
    @Autowired
    private JdbcLinkRepository linkRepository;

    @Autowired
    private JdbcClient jdbcClient;

    @Test
    @Transactional
    @Rollback
    public void saveTest() {
        Link link = new Link(0L, "https://www.google.com", OffsetDateTime.now());
        linkRepository.save(link);
        Link savedLink = jdbcClient.sql("SELECT * FROM link WHERE url = 'https://www.google.com'")
            .query(Link.class)
            .single();
        assertThat(savedLink.getUrl()).isEqualTo(link.getUrl());
        assertThat(savedLink.getLastCheckTime()).isNotNull();
        assertThat(savedLink.getId()).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        Link link = new Link(0L, "https://www.google.com", OffsetDateTime.now());
        Link saveLink = linkRepository.save(link);
        linkRepository.delete(saveLink.getId());
        List<Link> list = jdbcClient.sql("SELECT * FROM link")
            .query(Link.class)
            .list();

        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        OffsetDateTime startTime = OffsetDateTime.MIN;
        Link link = new Link(0L, "https://www.google.com", startTime);
        Link savedLink = linkRepository.save(link);
        OffsetDateTime updateTime = OffsetDateTime.now();
        linkRepository.update(savedLink.getId(), updateTime);

        Link updatedLink = jdbcClient.sql("SELECT * FROM link WHERE id = ?")
            .param(savedLink.getId())
            .query(Link.class)
            .single();

        assertTrue(updatedLink.getLastCheckTime().isAfter(startTime));
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrlTest() {
        Link link = new Link(0L, "https://www.google.com", OffsetDateTime.now());
        linkRepository.save(link);
        Optional<Link> savedLink = linkRepository.findByUrl(link.getUrl());
        assertThat(savedLink.isPresent()).isTrue();
        assertThat(savedLink.get().getUrl()).isEqualTo(link.getUrl());
        assertThat(savedLink.get().getLastCheckTime()).isNotNull();
        assertThat(savedLink.get().getId()).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllByLastCheckTimeBeforeTest() {
        Link link1 = new Link(0L, "https://www.google.com", OffsetDateTime.MIN);
        Link link2 = new Link(0L, "https://www.yandex.ru", OffsetDateTime.now());
        linkRepository.save(link1);
        linkRepository.save(link2);
        List<Link> links = linkRepository.findAllByLastCheckTimeBefore(OffsetDateTime.now().minusDays(1));
        assertThat(links.size()).isEqualTo(1);
        assertThat(links.getFirst().getUrl()).isEqualTo(link1.getUrl());
    }
}
