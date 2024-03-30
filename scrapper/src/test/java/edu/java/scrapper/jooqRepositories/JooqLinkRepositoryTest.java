package edu.java.scrapper.jooqRepositories;

import edu.java.models.dto.Link;
import edu.java.repositories.jooq.IJooqLinkRepository;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JooqLinkRepositoryTest extends IntegrationTest {
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jooq");
    }

    @Autowired
    private IJooqLinkRepository jooqLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void saveTest() {
        Link link = new Link(0L, "https://www.google.com", OffsetDateTime.now());
        jooqLinkRepository.save(link);
        Link savedLink = jooqLinkRepository.findByUrl(link.getUrl()).orElseThrow();
        assertThat(savedLink.getUrl()).isEqualTo(link.getUrl());
        assertThat(savedLink.getLastCheckTime()).isNotNull();
        assertThat(savedLink.getId()).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        Link link = new Link(0L, "https://www.google.com", OffsetDateTime.now());
        Link saveLink = jooqLinkRepository.save(link);
        jooqLinkRepository.delete(saveLink.getId());
        Optional<Link> optionalLink = jooqLinkRepository.findByUrl(saveLink.getUrl());

        assertFalse(optionalLink.isPresent());
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() {
        OffsetDateTime startTime = OffsetDateTime.now().minusDays(10);
        Link link = new Link(0L, "https://www.google.com", startTime);
        Link savedLink = jooqLinkRepository.save(link);
        OffsetDateTime updateTime = OffsetDateTime.now();
        jooqLinkRepository.update(savedLink.getId(), updateTime);

        Optional<Link> updatedLink = jooqLinkRepository.findByUrl(savedLink.getUrl());

        assertTrue(updatedLink.get().getLastCheckTime().isAfter(startTime));
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrlTest() {
        Link link = new Link(0L, "https://www.google.com", OffsetDateTime.now());
        jooqLinkRepository.save(link);
        Optional<Link> savedLink = jooqLinkRepository.findByUrl(link.getUrl());
        assertThat(savedLink.isPresent()).isTrue();
        assertThat(savedLink.get().getUrl()).isEqualTo(link.getUrl());
        assertThat(savedLink.get().getLastCheckTime()).isNotNull();
        assertThat(savedLink.get().getId()).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllByLastCheckTimeBeforeTest() {
        Link link1 = new Link(0L, "https://www.google.com", OffsetDateTime.now().minusDays(10));
        Link link2 = new Link(0L, "https://www.yandex.ru", OffsetDateTime.now());
        jooqLinkRepository.save(link1);
        jooqLinkRepository.save(link2);
        List<Link> links = jooqLinkRepository.findAllByLastCheckTimeBefore(OffsetDateTime.now().minusDays(1));
        assertThat(links.size()).isEqualTo(1);
        assertThat(links.getFirst().getUrl()).isEqualTo(link1.getUrl());
    }
}
