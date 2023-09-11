package com.woochacha.backend.domain.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class CommonTest implements WebMvcConfigurer {
    protected MockMvc mockMvc;

    @Mock
    protected ObjectMapper objectMapper;

    //    @Mock
//    private ProductServiceImpl productServiceImpl;
//
//    @Mock
//    private SignService signService;
//
//    @Mock
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Mock
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Mock
//    private SecurityFilterChain securityFilterChain;


}
