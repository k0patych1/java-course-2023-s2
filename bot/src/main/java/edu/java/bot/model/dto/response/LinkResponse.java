package edu.java.bot.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.net.URI;

@Data
@NoArgsConstructor
public class LinkResponse {
    @NotNull
    private Long id;

    @NotBlank
    private URI url;
}
