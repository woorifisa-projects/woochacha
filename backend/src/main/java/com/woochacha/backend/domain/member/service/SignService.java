package com.woochacha.backend.domain.member.service;

<<<<<<< HEAD
import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.dto.LoginSuccessDto;
import org.springframework.http.ResponseEntity;

public interface SignService {
    SignUpResponseDto signUp(SignUpRequestDto userRequestDto);

    ResponseEntity<LoginSuccessDto> login(LoginRequestDto loginRequestDto);
=======
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.dto.SignUpResponseDto;
import com.woochacha.backend.domain.member.entity.Member;

public interface SignService {
    SignUpResponseDto signUp(SignUpRequestDto userRequestDto);
>>>>>>> 32f9886682f2c2b121075c5bfc8026fb53aea5aa
}
