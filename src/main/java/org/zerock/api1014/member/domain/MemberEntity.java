package org.zerock.api1014.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {""})
public class MemberEntity {

    @Id
    private String email;

    private String pw;

    //MemberRole emum 추가후 작성
    private MemberRole role;



}
