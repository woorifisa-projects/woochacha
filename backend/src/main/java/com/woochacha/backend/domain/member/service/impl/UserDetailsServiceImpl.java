package com.woochacha.backend.domain.member.service.impl;

import com.woochacha.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// 인증에 필요한 UserDetailsService interface의 loadUserByUsername 메서드를 구현하는 클래스로
// loadUserByUsername 메서드를 통해 Database에 접근하여 사용자 정보를 가지고 온다.
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final MemberRepository memberRepository;

    // 유효한 사용자일 경우
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return (UserDetails) memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }


    //        Member member =  memberRepository.findByEmail(email);
//        if (member == null) {
//            throw new BadCredentialsException("사용자를 찾을 수 없습니다.");
//        } else {
//            return (UserDetails) member;
//        }

}
