package com.woochacha.backend.domain.member.entity;

<<<<<<< HEAD
import lombok.*;
=======
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
>>>>>>> 8b74967784446299c543987bc3b7485b05de69e9
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
<<<<<<< HEAD
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
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
    @NonNull
    private Boolean role;

    @NonNull
    private String email;

    @NonNull
//    @JsonProperty(access = Access.WRITE_ONLY) // Json 결과로 출력하지 않을 데이터에 대해 해당 어노테이션 설정 값 추가
    private String password;

    @NonNull
    private String name;

    @NonNull
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


    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    private static final long serialVersionUID = 6014984039564979072L;

    // 계정이 가지고 있는 권한 목록 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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
        return true;
    }

    // 비밀번호가 만료됐는지 리턴. true : 만료 X
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 계정이 활성화돼 있는지 리턴. true: 활성화
    @Override
    public boolean isEnabled() {
        return true;
    }
=======

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

// 유저 엔티티
@Entity
@Getter
@DynamicInsert
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MemberInfo memberInfo;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ColumnDefault("1")
    @NotBlank
    private short isAvailable;

    @ColumnDefault("1")
    @NotBlank
    private boolean status;

    private String profileImage;
>>>>>>> 8b74967784446299c543987bc3b7485b05de69e9
}
