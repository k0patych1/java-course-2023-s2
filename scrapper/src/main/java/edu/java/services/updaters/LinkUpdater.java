package edu.java.services.updaters;

import edu.java.configuration.ApplicationConfig;
import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.services.ILinkService;
import edu.java.services.ITgChatService;
import edu.java.services.updaters.chain.UpdatersChain;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkUpdater implements ILinkUpdater {
    private final Duration intervalOfCheck;

    private final List<? extends UpdatersChain> updaters;

    private final ILinkService linkService;

    private final ITgChatService tgChatService;

    @Autowired
    public LinkUpdater(ApplicationConfig applicationConfig,
                       List<? extends UpdatersChain> updaters, ILinkService linkService, ITgChatService tgChatService) {
        this.updaters = updaters;
        this.linkService = linkService;
        intervalOfCheck = applicationConfig.intervalCheckTime();
        this.tgChatService = tgChatService;
    }

    @Override
    public int update() {
        List<Link> links = linkService.listOldChecked(OffsetDateTime.now().minus(intervalOfCheck));
        int cntUpdated = 0;
        for (Link link : links) {
            for (UpdatersChain updater : updaters) {
                if (updater.canUpdate(link)) {
                    List<TgChat> tgChats = tgChatService.listAllWithLink(link.getId());
                    cntUpdated += updater.update(link, tgChats);
                    linkService.update(link.getId(), OffsetDateTime.now());
                }
            }
        }

        return cntUpdated;
    }
}
