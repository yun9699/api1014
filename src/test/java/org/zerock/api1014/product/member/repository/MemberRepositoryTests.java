package org.zerock.api1014.product.member.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.api1014.member.domain.MemberEntity;
import org.zerock.api1014.member.domain.MemberRole;
import org.zerock.api1014.member.repository.MemberRepository;


@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @Commit
    public void testDummies(){

        for (int i = 1; i <= 10; i++) {
            MemberEntity member = MemberEntity.builder()
                    .email("user"+i+"@aaa.com")
                    .pw(passwordEncoder.encode("1111"))
                    .role( i < 8 ? MemberRole.USER: MemberRole.ADMIN)
                    .build();
            memberRepository.save(member);
        }
    }
}
