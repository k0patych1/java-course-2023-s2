package edu.java.clients;

import model.AddLinkRequest;
import model.LinkResponse;
import model.RemoveLinkRequest;

public interface BotClient {
    LinkResponse addLink(AddLinkRequest addLinkRequest);

    LinkResponse removeLink(RemoveLinkRequest removeLinkRequest);
}
