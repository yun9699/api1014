package org.zerock.api1014.product.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "product")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String reviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    //하나의 리뷰는 한 상태이 관한 것이다
    //fetch LAZY 항상 걸기
    //연관관계 할때는 재귀호출을 막으려면 ToString exclude 하기
}
