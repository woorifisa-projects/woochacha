package com.woochacha.backend.domain.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woochacha.backend.domain.product.controller.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class CommonTest {
    private MockMvc mockMvc;

    @Mock
    private ObjectMapper objectMapper;

    public CommonTest(RestDocumentationContextProvider restDocumentation, ProductController controller) {

    }

    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation, Object controller) {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
        objectMapper = new ObjectMapper();
    }
}
