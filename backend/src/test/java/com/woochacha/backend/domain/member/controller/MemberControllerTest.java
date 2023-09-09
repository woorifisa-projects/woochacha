package com.woochacha.backend.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woochacha.backend.domain.common.CommonTest;
import com.woochacha.backend.domain.member.dto.LoginRequestDto;
import com.woochacha.backend.domain.member.dto.LoginResponseDto;
import com.woochacha.backend.domain.member.dto.SignResponseDto;
import com.woochacha.backend.domain.member.dto.SignUpRequestDto;
import com.woochacha.backend.domain.member.service.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MemberControllerTest extends CommonTest {

    @InjectMocks
    private MemberController memberController;

    @Mock
    private SignService signService;

    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
        objectMapper = new ObjectMapper();
    }

    // TODO: 이메일 중복, 핸드폰 번호 중복, 탈퇴 여부, 이용 제한 유저 조건 예외 처리별 테스트
    @Test
    @DisplayName("회원가입 - 성공")
    void registerUser() throws Exception {
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .email("testuser@woochacha.com")
                .password("testuser1234!")
                .name("박지은")
                .phone("01012345678")
                .profileImage("https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/default")
                .build();

        SignResponseDto signResponseDto = new SignResponseDto(true, 0, "Success");

        when(signService.signUp(any(SignUpRequestDto.class))).thenReturn(signResponseDto);

        mockMvc.perform(post("/users/register")
                        .content(objectMapper.writeValueAsString(signUpRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("users/register",
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("phone").description("핸드폰 번호"),
                                fieldWithPath("profileImage").description("기본 프로필 사진 S3 링크")
                        ),
                        responseFields(
                                fieldWithPath("success").description("회원가입 성공 여부"),
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("msg").description("메시지")
                        )))
                .andExpect(jsonPath("$.success").value(signResponseDto.isSuccess()))
                .andExpect(jsonPath("$.code").value(signResponseDto.getCode()))
                .andExpect(jsonPath("$.msg").value(signResponseDto.getMsg()))
                .andReturn();
    }

    @Test
    void login() throws Exception {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("testuser@woochacha.com")
                .password("testuser1234!")
                .build();

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .code(1)
                .msg("성공")
                .token("testToken")
                .id(1L)
                .name("박지은")
                .build();

        when(signService.login(any(LoginRequestDto.class))).thenReturn(loginResponseDto);

        mockMvc.perform(post("/users/login")
                        .content(objectMapper.writeValueAsString(loginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("users/login",
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("msg").description("메시지"),
                                fieldWithPath("token").description("Jwt 토큰"),
                                fieldWithPath("id").description("DB에 저장된 회원 아이디"),
                                fieldWithPath("name").description("이름")
                        ),
                        responseHeaders(
                                headerWithName("Authorization").description("Jwt 토큰")
                        )
                ))
                .andExpect(jsonPath("$.code").value(loginResponseDto.getCode()))
                .andExpect(jsonPath("$.msg").value(loginResponseDto.getMsg()))
                .andExpect(jsonPath("$.token").value(loginResponseDto.getToken()))
                .andExpect(jsonPath("$.id").value(loginResponseDto.getId()))
                .andExpect(jsonPath("$.name").value(loginResponseDto.getName()))
                .andReturn();
    }

    @Test
    void logout() throws Exception {
        Long memberId = 1L;
        when(signService.logout(any(Long.class))).thenReturn(true);

        mockMvc.perform(post("/users/logout")
                        .param("memberId", String.valueOf(memberId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("users/logout",
                        requestParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andReturn();
    }

    @Test
    void signOut() throws Exception {
        Long memberId = 1L;
        SignResponseDto signResponseDto = new SignResponseDto(true, 0, "Success");

        when(signService.signOut(any(Long.class), any(HttpServletRequest.class))).thenReturn(signResponseDto);

        mockMvc.perform(patch("/users/signout")
                        .param("memberId", String.valueOf(memberId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("users/signout",
                        requestParameters(
                                parameterWithName("memberId").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("success").description("회원 탈퇴 성공 여부"),
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("msg").description("메시지")
                        )))
                .andExpect(jsonPath("$.success").value(signResponseDto.isSuccess()))
                .andExpect(jsonPath("$.code").value(signResponseDto.getCode()))
                .andExpect(jsonPath("$.msg").value(signResponseDto.getMsg()))
                .andReturn();
    }
}