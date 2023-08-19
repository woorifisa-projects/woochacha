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
import javax.validation.constraints.NotBlank;
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
//    @NotBlank
    private Boolean role;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
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


<<<<<<< HEAD
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
=======
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    private static final long serialVersionUID = 6014984039564979072L;

    // 계정이 가지고 있는 권한 목록 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
    }

    // 계정 이름 리턴. 일반적으로 아이디 리턴
    @Override
    public String getUsername() {
        return Long.toString(this.id);
    }

    // 계정이 만료됐는지 리턴. true : 만료 X
    @Override
    public boolean isAccountNonExpired() {
<<<<<<< HEAD
        return this.status;
=======
        return true;
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
    }

    // 계정이 잠겨있는지 리턴. true: 잠김 X
    @Override
    public boolean isAccountNonLocked() {
<<<<<<< HEAD
        return this.isAvailable;
=======
        return true;
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
    }

    // 비밀번호가 만료됐는지 리턴. true : 만료 X
    @Override
    public boolean isCredentialsNonExpired() {
<<<<<<< HEAD
        return true;
=======
        return false;
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
    }

    // 계정이 활성화돼 있는지 리턴. true: 활성화
    @Override
    public boolean isEnabled() {
        return true;
    }
}
