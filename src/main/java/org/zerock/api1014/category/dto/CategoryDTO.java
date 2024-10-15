package org.zerock.api1014.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDTO {

    private Long cno;

    @NotNull
    private String cname;
}
