package edu.java.bot.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.net.URI;

@Data
@NoArgsConstructor
public class RemoveLinkRequest {
    @NotBlank
    private URI link;
}
