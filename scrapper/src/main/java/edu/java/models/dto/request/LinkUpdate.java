package edu.java.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkUpdate {
    @NotNull
    private Long id;

    @NotBlank
    private URI url;

    @NotNull
    private String description;

    @NotEmpty
    private List<Long> tgChatIds;
}
