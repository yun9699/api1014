package org.zerock.api1014.cart.domain;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.api1014.member.domain.MemberEntity;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {""})
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    @OneToOne
    private MemberEntity member;


}
