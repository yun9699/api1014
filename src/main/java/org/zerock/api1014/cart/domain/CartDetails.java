package org.zerock.api1014.cart.domain;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.api1014.product.domain.Product;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {""})
public class CartDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int qty;
}
