package com.woochacha.backend.domain.sendSMS.repository;

import com.woochacha.backend.domain.sendSMS.entity.AuthPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthPhoneRepository extends JpaRepository<AuthPhone, String> {

    @Modifying
    @Query("UPDATE AuthPhone a SET a.authStatus = :authStatus WHERE a.phone = :phone")
    void updateAuthStatus(@Param("authStatus") Byte authStatus, @Param("phone") String phone);
}
