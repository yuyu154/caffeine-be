package com.woowacourse.caffeine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.application.dto.ShopResponses;
import com.woowacourse.caffeine.application.service.MenuItemService;
import com.woowacourse.caffeine.application.service.ShopService;
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

import java.util.Arrays;
import java.util.Collections;

import static com.woowacourse.caffeine.controller.ShopController.V1_SHOP;
import static com.woowacourse.caffeine.utils.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.caffeine.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
        final ShopCreateRequest shopCreateRequest = new ShopCreateRequest();
        shopCreateRequest.setName("new store");
        final ShopResponse shopResponse = new ShopResponse(200L, "new store");
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
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("name").description("new store")
                )));
    }

    @Test
    @DisplayName("상점 조회 문서")
    void retrieve_shop() throws Exception {
        //given
        final long id = 100L;
        ShopResponse shopResponse = new ShopResponse(id, "가게1");
        given(shopService.findById(id)).willReturn(shopResponse);

        //when
        ResultActions result = mockMvc.perform(
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
        ResultActions result = mockMvc.perform(
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

        //given
        ShopResponse shopResponse1 = new ShopResponse(
            100L,
            "어디야 커피 잠실점",
            "https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_%EC%84%9D%EC%B4%8C%ED%98%B8%EC%88%98.jpg?raw=true",
            "서울특별시 송파구 석촌호수로 262 (송파동)",
            "02-758-8693");
        ShopResponse shopResponse2 = new ShopResponse(
            101L,
            "석촌 호수",
            "https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_%EC%86%A1%ED%8C%8C%EA%B5%AC%EC%B2%AD.jpeg?raw=true",
            "서울특별시 송파구 오금로 142 (송파동)",
            "02-421-3622");
        ShopResponses shopResponses = new ShopResponses();
        shopResponses.setShopResponses(Arrays.asList(shopResponse1, shopResponse2));
        given(shopService.findAll()).willReturn(shopResponses);

        //when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get(String.format("%s", V1_SHOP))
        );

        //then
        result.andExpect(status().isOk())
            .andDo(print())
            .andDo(document("shop-list",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters()
            ));
    }
}
