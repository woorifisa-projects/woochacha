package com.woochacha.backend.domain.member.repository;

import com.woochacha.backend.domain.admin.dto.manageMember.GetMemberDto;
import com.woochacha.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Modifying
    @Query("UPDATE Member m SET m.status = :status WHERE m.id = :memberId")
    int updateMemberStatus(@Param("memberId") Long memberId, @Param("status") Byte status);
    @Modifying
    @Query("UPDATE Member m SET m.profileImage = :profileImage WHERE m.id = :memberId")
    int updateMemberImage(@Param("memberId") Long memberId, @Param("profileImage") String profileImage);

    @Modifying
    @Query("UPDATE Member m SET m.isAvailable = :isAvailable WHERE m.id = :memberId")
    int deleteMember(@Param("memberId") Long memberId, @Param("isAvailable") Byte isAvailable);

    @Query("SELECT new com.woochacha.backend.domain.admin.dto.manageMember.GetMemberDto(" +
            "m.isAvailable, m.name, m.email ,m.phone, m.createdAt, m.profileImage) " +
            "FROM Member m  WHERE m.id = :memberId")
    GetMemberDto getMemberInfo(@Param("memberId") Long memberId);

}
