package com.woowacourse.caffeine.presentation.controller.documentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.caffeine.application.dto.LoginRequest;
import com.woowacourse.caffeine.application.dto.OwnerResponse;
import com.woowacourse.caffeine.application.dto.SignUpRequest;
import com.woowacourse.caffeine.application.service.OwnerService;
import com.woowacourse.caffeine.presentation.controller.OwnerController;
import com.woowacourse.caffeine.utils.TestJsonUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static com.woowacourse.caffeine.presentation.controller.OwnerController.V1_OWNER;
import static com.woowacourse.caffeine.utils.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.caffeine.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
@AutoConfigureRestDocs
public class OwnerDocumentationTest {

    private static final String OWNER_CREATE_REQUEST_JSON = "/json/owner/owner_create_request.json";
    private static final String OWNER_LOGIN_REQUEST_JSON = "/json/owner/owner_login_request.json";
    private static final String OWNER_RESPONSE_JSON = "/json/owner/owner_response.json";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @Test
    @DisplayName("회원 가입")
    void signUp() throws Exception {
        final SignUpRequest signUpRequest =
            TestJsonUtils.readJson(OWNER_CREATE_REQUEST_JSON, SignUpRequest.class);

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(V1_OWNER)
                .content(new ObjectMapper().writeValueAsString(signUpRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("owner-create",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        final LoginRequest loginRequest =
            TestJsonUtils.readJson(OWNER_LOGIN_REQUEST_JSON, LoginRequest.class);

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(V1_OWNER + "/login")
                .content(new ObjectMapper().writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(print())
            .andDo(document("owner-login",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    @DisplayName("로그아웃")
    void logout() throws Exception {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(V1_OWNER + "/logout")
                .sessionAttr("email", "test@mail.com")
        ).andExpect(status().isOk())
            .andDo(print())
            .andDo(document("owner-logout",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    @DisplayName("본인 조회")
    void findMe() throws Exception {
        final String email = "test@mail.com";
        final OwnerResponse ownerResponse =
            TestJsonUtils.readJson(OWNER_RESPONSE_JSON, OwnerResponse.class);
        given(ownerService.findByEmail(email)).willReturn(ownerResponse);

        mockMvc.perform(
            RestDocumentationRequestBuilders.get(V1_OWNER + "/me")
                .sessionAttr("email", email)
        ).andExpect(status().isOk())
            .andDo(print())
            .andDo(document("owner-me-find",
                getDocumentRequest(),
                getDocumentResponse()));
    }
}
