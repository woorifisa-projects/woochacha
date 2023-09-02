package com.woochacha.backend.domain.member.repository;

import com.woochacha.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(long id);
    Optional <Member> findByEmail(String email);
}
