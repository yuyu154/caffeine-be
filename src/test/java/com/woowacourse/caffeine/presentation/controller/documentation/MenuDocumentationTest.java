package com.woowacourse.caffeine.presentation.controller.documentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.caffeine.application.dto.MenuCreateRequest;
import com.woowacourse.caffeine.application.dto.MenuItemResponse;
import com.woowacourse.caffeine.application.dto.MenuItemUpdateRequest;
import com.woowacourse.caffeine.application.service.MenuItemService;
import com.woowacourse.caffeine.presentation.controller.MenuController;
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

import static com.woowacourse.caffeine.presentation.controller.MenuController.V1_MENU;
import static com.woowacourse.caffeine.utils.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.caffeine.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuController.class)
@AutoConfigureRestDocs
public class MenuDocumentationTest {

    private static final String MENU_CREATE_REQUEST_JSON_PATH = "/json/menu/menu_create_request.json";
    private static final String MENU_RESPONSE_JSON = "/json/menu/menu_response.json";
    private static final String MENU_UPDATE_REQUEST_JSON = "/json/menu/menu_update_request.json";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuItemService menuItemService;

    @Test
    @DisplayName("메뉴를 등록하는 문서")
    void createMenuItem() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();

        final MenuCreateRequest menuCreateRequest =
            TestJsonUtils.readJson(MENU_CREATE_REQUEST_JSON_PATH, MenuCreateRequest.class);
        final MenuItemResponse menuItemResponse =
            TestJsonUtils.readJson(MENU_RESPONSE_JSON, MenuItemResponse.class);

        given(menuItemService.createMenuItem(any())).willReturn(menuItemResponse);

        this.mockMvc.perform(
            RestDocumentationRequestBuilders.post(V1_MENU)
                .content(objectMapper.writeValueAsString(menuCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("menu-item-create",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    @DisplayName("단일 메뉴를 조회")
    void findMenuItem() throws Exception {
        final MenuItemResponse menuItemResponse =
            TestJsonUtils.readJson(MENU_RESPONSE_JSON, MenuItemResponse.class);
        final long menuItemId = menuItemResponse.getId();

        given(menuItemService.findByMenuItemId(menuItemId)).willReturn(menuItemResponse);

        this.mockMvc.perform(
            RestDocumentationRequestBuilders.get(String.format(V1_MENU + "/%d", menuItemId))
        ).andExpect(status().isOk())
            .andDo(print())
            .andDo(document("menu-item-find",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("menuItemId").description("메뉴 아이디").optional()
                )));
    }

    @Test
    @DisplayName("메뉴를 변경하는 문서")
    void updateMenuItem() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();

        final MenuItemUpdateRequest menuItemUpdateRequest =
            TestJsonUtils.readJson(MENU_UPDATE_REQUEST_JSON, MenuItemUpdateRequest.class);
        final long menuItemId = 1L;

        this.mockMvc.perform(
            RestDocumentationRequestBuilders.put(String.format(V1_MENU + "/%d", menuItemId))
                .content(objectMapper.writeValueAsString(menuItemUpdateRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(print())
            .andDo(document("menu-item-update",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("menuItemId").description("메뉴 아이디").optional()
                )));
    }

    @Test
    @DisplayName("메뉴를 삭제하는 문서")
    void deleteMenuItem() throws Exception {
        final long menuItemId = 1L;

        this.mockMvc.perform(
            RestDocumentationRequestBuilders.delete(String.format(V1_MENU + "/%d", menuItemId))
        ).andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("menu-item-delete",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("menuItemId").description("메뉴 아이디").optional()
                )));
    }
}
