package edu.java.bot.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkResponse {
    @NotNull
    private Long id;

    @NotBlank
    private URI url;
}
