package org.zerock.api1014.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.api1014.cart.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {


}
