package com.woowacourse.caffeine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.application.dto.ShopResponses;
import com.woowacourse.caffeine.application.service.MenuItemService;
import com.woowacourse.caffeine.application.service.ShopService;
import com.woowacourse.caffeine.mock.ShopResponseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static com.woowacourse.caffeine.controller.ShopController.V1_SHOP;
import static com.woowacourse.caffeine.utils.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.caffeine.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopController.class)
@AutoConfigureRestDocs
public class ShopDocumentationTest {
    private static final long DEFAULT_SHOP_ID = 100L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @MockBean
    private MenuItemService menuItemService;

    @Test
    @DisplayName("상점 생성 문서")
    void create_shop() throws Exception {
        //given
        final ShopResponse shopResponse = ShopResponseRepository.shopResponse1;
        final ShopCreateRequest shopCreateRequest = new ShopCreateRequest(
            shopResponse.getName(),
            shopResponse.getImage(),
            shopResponse.getAddress(),
            shopResponse.getPhoneNumber()
        );
        given(shopService.createShop(any())).willReturn(shopResponse);

        //when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.post(String.format("%s", V1_SHOP))
                .content(new ObjectMapper().writeValueAsString(shopCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("shop-create",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    @DisplayName("상점 조회 문서")
    void retrieve_shop() throws Exception {
        //given
        final long id = 100L;
        ShopResponse shopResponse = ShopResponseRepository.shopResponse1;
        given(shopService.findById(id)).willReturn(shopResponse);

        //when
        final ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get(String.format("%s/{id}", V1_SHOP), id)
        );

        //then
        result.andExpect(status().isOk())
            .andDo(print())
            .andDo(document("shop-retrieve",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("id").description("가게 아이디")
                ),
                responseFields(
                    fieldWithPath("id").description("상점 아이디"),
                    fieldWithPath("name").description("상점 이름"),
                    fieldWithPath("image").description("Image URL"),
                    fieldWithPath("address").description("Address"),
                    fieldWithPath("phoneNumber").description("Phone Number")
                )));
    }

    @Test
    @DisplayName("상점 별 메뉴 목록 조회 문서")
    void menus_by_shop() throws Exception {

        //given
        given(menuItemService.findByShopId(DEFAULT_SHOP_ID)).willReturn(Collections.emptyList());

        //when
        final ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get(String.format("%s/{id}/menus", V1_SHOP), DEFAULT_SHOP_ID)
        );

        //then
        result.andExpect(status().isOk())
            .andDo(print())
            .andDo(document("shop-menu-list",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("id").description("가게 아이디")
                )));
    }

    @Test
    @DisplayName("모든 상점 목록 조회 문서")
    void find_all_shop() throws Exception {

        //given & when
        ShopResponses shopResponses = ShopResponseRepository.shopResponses;

        given(shopService.findAll()).willReturn(shopResponses);

        //then
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get(String.format("%s", V1_SHOP))
        );

        result.andExpect(status().isOk())
            .andDo(print())
            .andDo(document("shop-list",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters()
            ));
    }
}
