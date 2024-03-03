package edu.java.bot.clients;

import edu.java.models.dto.request.AddLinkRequest;
import edu.java.models.dto.request.RemoveLinkRequest;
import edu.java.models.dto.response.LinkResponse;
import edu.java.models.dto.response.ListLinksResponse;

public interface ScrapperClient {
    String registerChat(Long id);

    String deleteChat(Long id);

    ListLinksResponse getLinks(Long id);

    LinkResponse addLink(Long id, AddLinkRequest addLinkRequest);

    LinkResponse removeLink(Long id, RemoveLinkRequest removeLinkRequest);
}
