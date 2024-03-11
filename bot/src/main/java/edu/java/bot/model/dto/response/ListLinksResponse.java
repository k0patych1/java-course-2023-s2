package edu.java.bot.model.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListLinksResponse {
    @NotEmpty
    private List<LinkResponse> links;

    @NotNull
    private Integer size;
}
