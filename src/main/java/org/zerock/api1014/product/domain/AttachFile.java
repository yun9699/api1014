package org.zerock.api1014.product.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
//엔티티가 아닐때 사용
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AttachFile {

    private int ord;
    private String fileName;

}
