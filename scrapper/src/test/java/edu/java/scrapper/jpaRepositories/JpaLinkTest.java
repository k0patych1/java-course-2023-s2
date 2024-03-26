package edu.java.scrapper.jpaRepositories;

import edu.java.domain.jpa.Link;
import edu.java.repositories.jpa.IJpaLinkRepository;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JpaLinkTest extends IntegrationTest {
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
    }

    @Autowired
    private IJpaLinkRepository jpaLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void saveTest() {
        Link link = new Link();
        OffsetDateTime time = OffsetDateTime.now();
        link.setLastCheckTime(time);
        link.setUrl("https://stackoverflow.com/questions/7");
        Link savedLink = jpaLinkRepository.save(link);
        assertThat(link).isEqualTo(savedLink);
    }

    @Test
    @Transactional
    @Rollback
    public void findByIdTest() {
        Link link = new Link();
        OffsetDateTime time = OffsetDateTime.now();
        link.setLastCheckTime(time);
        link.setUrl("https://stackoverflow.com/questions/7");
        jpaLinkRepository.save(link);
        jpaLinkRepository.save(link);
        assertThat(jpaLinkRepository.findById(link.getId()).get()).isEqualTo(link);
        assertThat(jpaLinkRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrlTest() {
        Link link = new Link();
        OffsetDateTime time = OffsetDateTime.now();
        link.setLastCheckTime(time);
        link.setUrl("https://stackoverflow.com/questions/7");
        jpaLinkRepository.save(link);
        assertThat(jpaLinkRepository.findByUrl(link.getUrl()).get()).isEqualTo(link);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllByLastCheckTimeBeforeTest() {
        Link link1 = new Link();
        OffsetDateTime time1 = OffsetDateTime.now().minusDays(10);
        link1.setLastCheckTime(time1);
        link1.setUrl("https://stackoverflow.com/questions/7");
        jpaLinkRepository.save(link1);

        Link link2 = new Link();
        OffsetDateTime time2 = OffsetDateTime.now().plusDays(10);
        link2.setUrl("https://stackoverflow.com/questions/8");
        link2.setLastCheckTime(time2);
        jpaLinkRepository.save(link2);

        List<Link> timeLinks = jpaLinkRepository.findAllByLastCheckTimeBefore(OffsetDateTime.now());

        assertThat(timeLinks.size())
            .isEqualTo(1);
        assertThat(timeLinks.getFirst())
            .isEqualTo(link1);
    }
}
