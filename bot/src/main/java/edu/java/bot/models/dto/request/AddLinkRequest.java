package edu.java.bot.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.net.URI;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddLinkRequest {
    @NotBlank
    private URI link;
}
