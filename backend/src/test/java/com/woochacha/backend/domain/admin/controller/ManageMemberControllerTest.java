package com.woochacha.backend.domain.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.manageMember.*;
import com.woochacha.backend.domain.admin.service.ManageMemberService;
import com.woochacha.backend.domain.common.CommonTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ManageMemberControllerTest extends CommonTest {

    @InjectMocks
    private ManageMemberController manageMemberController;

    @Mock
    private ManageMemberService manageMemberService;

    @BeforeEach
    void init(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(manageMemberController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("전체 사용자 정보를 조회한다.")
    void getMemberInfoList() throws Exception {
        MemberInfoListResponseDto memberInfoListDto = MemberInfoListResponseDto.builder()
                .id(1L)
                .email("user23@woochacha.com")
                .name("류빈용")
                .phone("01049153041")
                .isAvailable((byte) 1)
                .build();
        List<MemberInfoListResponseDto> result = new ArrayList<>();
        result.add(memberInfoListDto);

        Pageable pageable = PageRequest.of(0, 5);

        QueryResults<MemberInfoListResponseDto> memberInfoDtoQueryResults = new QueryResults<>(result, 5L, 0L, 10L);

        when(manageMemberService.getAllMemberInfo(any())).thenReturn(new PageImpl<>(
                memberInfoDtoQueryResults.getResults(), pageable, memberInfoDtoQueryResults.getTotal()));

        mockMvc.perform(get("/admin/members")
                        .param("pageable", String.valueOf(pageable))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("admin/member/get-all",
                        requestParameters(
                                parameterWithName("pageable").description("페이지네이션")
                        ),
                        responseFields(
                                fieldWithPath("content[].id").description("사용자 아이디"),
                                fieldWithPath("content[].name").description("사용자 이름"),
                                fieldWithPath("content[].email").description("사용자 이메일"),
                                fieldWithPath("content[].phone").description("사용자 전화번호"),
                                fieldWithPath("content[].isAvailable").description("사용자 활동권한"),

                                fieldWithPath("pageable.sort.empty").description("정렬 여부 (비어 있음)"),
                                fieldWithPath("pageable.sort.sorted").description("정렬 여부 (정렬됨)"),
                                fieldWithPath("pageable.sort.unsorted").description("정렬 여부 (정렬되지 않음)"),

                                fieldWithPath("pageable.offset").description("페이지 오프셋 값"),
                                fieldWithPath("pageable.pageSize").description("페이지 크기"),
                                fieldWithPath("pageable.pageNumber").description("현재 페이지 번호"),
                                fieldWithPath("pageable.paged").description("페이지 여부 (페이징된 경우 true, 그렇지 않으면 false)"),
                                fieldWithPath("pageable.unpaged").description("페이징되지 않은 경우 true, 그렇지 않으면 false"),

                                fieldWithPath("pageable").description("페이징 정보"),
                                fieldWithPath("last").description("마지막 페이지 여부"),
                                fieldWithPath("totalPages").description("총 페이지 수"),
                                fieldWithPath("totalElements").description("총 요소 수"),
                                fieldWithPath("size").description("페이지 크기"),
                                fieldWithPath("number").description("현재 페이지 번호"),
                                fieldWithPath("sort.empty").description("정렬 여부 (비어 있음)"),
                                fieldWithPath("sort.sorted").description("정렬 여부 (정렬됨)"),
                                fieldWithPath("sort.unsorted").description("정렬 여부 (정렬되지 않음)"),

                                fieldWithPath("numberOfElements").description("현재 페이지의 요소 수"),
                                fieldWithPath("first").description("첫 번째 페이지 여부"),
                                fieldWithPath("empty").description("결과가 비어 있는지 여부")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content[0].id").value(memberInfoListDto.getId()))
                .andExpect(jsonPath("content[0].name").value(memberInfoListDto.getName()))
                .andExpect(jsonPath("content[0].email").value(memberInfoListDto.getEmail()))
                .andExpect(jsonPath("content[0].phone").value(memberInfoListDto.getPhone()))
                .andExpect(jsonPath("content[0].isAvailable").value((int) memberInfoListDto.getIsAvailable()))
                .andReturn();
    }

    @Test
    @DisplayName("특정 사용자 정보를 조회한다.")
    void getMemberInfo() throws Exception {
        MemberInfoDto memberInfoDto = MemberInfoDto.builder()
                .isAvailable((byte)1)
                .name("류빈용")
                .email("user23@woochacha.com")
                .phone("01049153041")
                .createdAt(LocalDateTime.now())
                .profileImage("https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/default")
                .build();

        MemberInfoResponseDto memberInfoResponseDto = MemberInfoResponseDto.builder()
                .memberInfoDto(memberInfoDto)
                .onSale(0)
                .completeSale(0)
                .onPurchase(0)
                .completePurchase(0)
                .build();

        Long memberId = 17L;
        when(manageMemberService.getMemberInfo(any())).thenReturn(memberInfoResponseDto);

        LocalDateTime time = memberInfoDto.getCreatedAt();

        mockMvc.perform(RestDocumentationRequestBuilders.get("/admin/members/{memberId}", memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
                .andDo(document("admin/member/get-detail",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 아이디")
                        ),
                        responseFields(
                                fieldWithPath("memberInfoDto.isAvailable").description("사용자 활동권한"),
                                fieldWithPath("memberInfoDto.name").description("사용자 이름"),
                                fieldWithPath("memberInfoDto.email").description("사용자 이메일"),
                                fieldWithPath("memberInfoDto.phone").description("사용자 전화번호"),
                                fieldWithPath("memberInfoDto.createdAt").description("가입일자"),
                                fieldWithPath("memberInfoDto.profileImage").description("사용자 프로필 사진"),
                                fieldWithPath("onSale").description("판매 중인 상품 수"),
                                fieldWithPath("completeSale").description("판매 완료한 상품 수"),
                                fieldWithPath("onPurchase").description("구매 대기 중인 상품 수"),
                                fieldWithPath("completePurchase").description("구매한 상품 수")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("memberInfoDto.isAvailable").value(1))
                .andExpect(jsonPath("memberInfoDto.name").value(memberInfoDto.getName()))
                .andExpect(jsonPath("memberInfoDto.email").value(memberInfoDto.getEmail()))
                .andExpect(jsonPath("memberInfoDto.phone").value(memberInfoDto.getPhone()))

                .andExpect(jsonPath("$.memberInfoDto.createdAt[0]").value(time.getYear()))
                .andExpect(jsonPath("$.memberInfoDto.createdAt[1]").value(time.getMonthValue()))
                .andExpect(jsonPath("$.memberInfoDto.createdAt[2]").value(time.getDayOfMonth()))
                .andExpect(jsonPath("$.memberInfoDto.createdAt[3]").value(time.getHour()))
                .andExpect(jsonPath("$.memberInfoDto.createdAt[5]").value(time.getSecond()))
                .andExpect(jsonPath("$.memberInfoDto.createdAt[6]").value(time.getNano()))
                .andExpect(jsonPath("$.memberInfoDto.createdAt[6]").value(time.getNano()))

                .andExpect(jsonPath("memberInfoDto.profileImage").value(memberInfoDto.getProfileImage()))
                .andExpect(jsonPath("$.onSale").value(memberInfoResponseDto.getOnSale()))
                .andExpect(jsonPath("$.completeSale").value(memberInfoResponseDto.getCompleteSale()))
                .andExpect(jsonPath("$.onPurchase").value(memberInfoResponseDto.getOnPurchase()))
                .andExpect(jsonPath("$.completePurchase").value(memberInfoResponseDto.getCompletePurchase()))
                .andReturn();
    }

    @Test
    @DisplayName("회원 정보를 변경한다.")
    void editMemberInfo() throws Exception {
        Long memberId = 17L;

        EditMemberRequestDto editMemberRequestDto = EditMemberRequestDto.builder()
                .isChecked(1)
                .isAvailable((byte) 1)
                .build();

        String returnMessage = "Member status updated successfully.";

        when(manageMemberService.updateMemberInfo(any(), any())).thenReturn(returnMessage);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/admin/members/edit/{memberId}", memberId)
                        .content(objectMapper.writeValueAsString(editMemberRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andDo(print())
                .andDo(document("admin/member/edit",
                        pathParameters(
                                parameterWithName("memberId").description("수정할 회원 아이디")
                        ),
                        requestFields(
                                fieldWithPath("isChecked").description("기본 프로필로 수정 여부"),
                                fieldWithPath("isAvailable").description("이용 제한 선택 여부")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string(returnMessage))
                .andReturn();
    }

    @Test
    @DisplayName("회원을 탈퇴시킨다")
    void deleteMemberInfo() throws Exception {
        Long memberId = 17L;
        String returnMessage = "Member delete successfully.";

        when(manageMemberService.deleteMember(any())).thenReturn(returnMessage);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/admin/members/delete/{memberId}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andDo(print())
                .andDo(document("admin/member/delete",
                        pathParameters(
                                parameterWithName("memberId").description("탈퇴시킬 회원 아이디")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string(returnMessage))
                .andReturn();
    }

    @Test
    @DisplayName("회원 로그를 조회한다")
    void memberLogInfoList() throws Exception {
        Long memberId = 17L;

        MemberLogDto memberLogDto = MemberLogDto.builder()
                .email("test@naver.com")
                .name("김철수")
                .date(LocalDateTime.now())
                .description("로그인")
                .etc("/login")
                .build();

        List<MemberLogDto> result = new ArrayList<>();
        result.add(memberLogDto);

        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        QueryResults<MemberLogDto> memberInfoDtoQueryResults = new QueryResults<>(result, 5L, 0L, 10L);
        Page<MemberLogDto> resultPage = new PageImpl<>(memberInfoDtoQueryResults.getResults(), pageable, memberInfoDtoQueryResults.getTotal());
        doReturn(resultPage).when(manageMemberService).getMemberLog(any(), eq(page), eq(size));

        LocalDateTime time = memberLogDto.getDate();

        mockMvc.perform(RestDocumentationRequestBuilders.get("/admin/members/log/{memberId}", memberId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("admin/member/log",
                        pathParameters(
                                parameterWithName("memberId").description("사용자 아이디")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지 오프셋"),
                                parameterWithName("size").description("페이지당 보여줄 로그 개수")
                        ),
                        responseFields(
                                fieldWithPath("content[]..email").description("사용자 이메일"),
                                fieldWithPath("content[]..name").description("사용자 이름"),
                                fieldWithPath("content[]..date").description("활동일"),
                                fieldWithPath("content[]..description").description("설명"),
                                fieldWithPath("content[]..etc").description("활동 링크"),

                                fieldWithPath("pageable.sort.empty").description("정렬 여부 (비어 있음)"),
                                fieldWithPath("pageable.sort.sorted").description("정렬 여부 (정렬됨)"),
                                fieldWithPath("pageable.sort.unsorted").description("정렬 여부 (정렬되지 않음)"),

                                fieldWithPath("pageable.offset").description("페이지 오프셋 값"),
                                fieldWithPath("pageable.pageSize").description("페이지 크기"),
                                fieldWithPath("pageable.pageNumber").description("현재 페이지 번호"),
                                fieldWithPath("pageable.paged").description("페이지 여부 (페이징된 경우 true, 그렇지 않으면 false)"),
                                fieldWithPath("pageable.unpaged").description("페이징되지 않은 경우 true, 그렇지 않으면 false"),

                                fieldWithPath("pageable").description("페이징 정보"),
                                fieldWithPath("last").description("마지막 페이지 여부"),
                                fieldWithPath("totalPages").description("총 페이지 수"),
                                fieldWithPath("totalElements").description("총 요소 수"),
                                fieldWithPath("size").description("페이지 크기"),
                                fieldWithPath("number").description("현재 페이지 번호"),
                                fieldWithPath("sort.empty").description("정렬 여부 (비어 있음)"),
                                fieldWithPath("sort.sorted").description("정렬 여부 (정렬됨)"),
                                fieldWithPath("sort.unsorted").description("정렬 여부 (정렬되지 않음)"),

                                fieldWithPath("numberOfElements").description("현재 페이지의 요소 수"),
                                fieldWithPath("first").description("첫 번째 페이지 여부"),
                                fieldWithPath("empty").description("결과가 비어 있는지 여부")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").value(memberLogDto.getEmail()))
                .andExpect(jsonPath("$.content[0].name").value(memberLogDto.getName()))

                .andExpect(jsonPath("$.content[0].date[0]").value(time.getYear()))
                .andExpect(jsonPath("$.content[0].date[1]").value(time.getMonthValue()))
                .andExpect(jsonPath("$.content[0].date[2]").value(time.getDayOfMonth()))
                .andExpect(jsonPath("$.content[0].date[3]").value(time.getHour()))
                .andExpect(jsonPath("$.content[0].date[5]").value(time.getSecond()))
                .andExpect(jsonPath("$.content[0].date[6]").value(time.getNano()))

                .andExpect(jsonPath("$.content[0].description").value(memberLogDto.getDescription()))
                .andExpect(jsonPath("$.content[0].etc").value(memberLogDto.getEtc()))
                .andReturn();
    }
}