package org.zerock.api1014.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.api1014.member.domain.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {


}
