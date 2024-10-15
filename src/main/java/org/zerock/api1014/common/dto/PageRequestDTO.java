package org.zerock.api1014.common.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    @Builder.Default
    @Min(value = 1, message = "over 1")
    private int page = 1;

    @Builder.Default
    @Min(value = 10, message = "set over 10")
    @Max(value = 100, message = "cannot over 100")
    private int size = 10;
}
