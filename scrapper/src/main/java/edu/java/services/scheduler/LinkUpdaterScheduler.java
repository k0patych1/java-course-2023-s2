package edu.java.services.scheduler;

import edu.java.services.updaters.LinkUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        linkUpdater.update();
    }
}
