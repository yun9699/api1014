// mapping 이라는 용어로도 사용한다
package org.zerock.api1014.category.domain;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.api1014.product.domain.Product;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"product", "category"})
public class CategoryProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpno;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Category category;
}
