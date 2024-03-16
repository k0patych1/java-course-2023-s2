package edu.java.services.updaters.chain;

import edu.java.models.dto.Link;

public interface UpdatersChain {
    boolean canUpdate(Link link);

    int update(Link link);
}
