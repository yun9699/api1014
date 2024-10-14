package org.zerock.api1014.cart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.api1014.cart.domain.CartDetails;

public interface CartDetailsRepository extends JpaRepository<CartDetails, Long> {

    @Query("""
            SELECT p, count(r) 
            FROM 
                MemberEntity m 
                left join Cart c ON c.member = m
                left join CartDetails cd ON cd.cart = c
                join Product p ON p = cd.product
                left join Review r ON r.product = p
            where m.email = :email
            """) // 상품과 리뷰의 개수 뽑기
    Page<Object[]> listOfMember(@Param("email") String email, Pageable pageable);


}
