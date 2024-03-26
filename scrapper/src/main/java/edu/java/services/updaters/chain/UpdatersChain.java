package edu.java.services.updaters.chain;

import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import java.util.List;

public interface UpdatersChain {
    boolean canUpdate(Link link);

    int update(Link link, List<TgChat> tgChats);
}
