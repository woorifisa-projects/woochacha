package com.woochacha.backend.domain.member.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "member")
@DynamicInsert
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault("1")
//    @NotNull
    private Boolean role;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ColumnDefault("1")
    private Boolean isAvailable;

    @ColumnDefault("1")
    private Boolean status;

    private String profileImage;


//    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
//    private List<String> roles = new ArrayList<>();
//
//    // 계정이 가지고 있는 권한 목록 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
        return null;
    }

    // 계정 이름 리턴. 일반적으로 아이디 리턴
    @Override
    public String getUsername() {
        return Long.toString(this.id);
    }

    // 계정이 만료됐는지 리턴. true : 만료 X
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있는지 리턴. true: 잠김 X
    @Override
    public boolean isAccountNonLocked() {
        return this.isAvailable;
    }

    // 비밀번호가 만료됐는지 리턴. true : 만료 X
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화돼 있는지 리턴. true: 활성화
    @Override
    public boolean isEnabled() {
        return this.status;
    }
}
