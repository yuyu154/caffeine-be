package com.woowacourse.caffeine.presentation.controller.documentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.caffeine.application.dto.OrderCreateRequest;
import com.woowacourse.caffeine.application.dto.OrderResponse;
import com.woowacourse.caffeine.application.service.OrderService;
import com.woowacourse.caffeine.presentation.controller.OrderController;
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

import java.util.Arrays;
import java.util.List;

import static com.woowacourse.caffeine.presentation.controller.ShopController.V1_SHOP;
import static com.woowacourse.caffeine.utils.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.caffeine.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureRestDocs
public class OrderDocumentationTest {

    private static final String ORDER_CREATE_REQUEST_JSON = "/json/order/order_create_request.json";
    private static final String ORDER_RESPONSE_JSON = "/json/order/order_response.json";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("주문 생성")
    void createOrder() throws Exception {
        //given
        final long shopId = 1L;
        final long orderId = 1L;
        final OrderCreateRequest orderCreateRequest =
            TestJsonUtils.readJson(ORDER_CREATE_REQUEST_JSON, OrderCreateRequest.class);

        given(orderService.create(eq(shopId), any(), eq("1"))).willReturn(orderId);

        // when & then

        this.mockMvc.perform(
            RestDocumentationRequestBuilders.post(getUri(shopId))
                .content(new ObjectMapper().writeValueAsString(orderCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("order-create",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    @DisplayName("주문 단일 조회 (id)")
    void findById() throws Exception {
        final long shopId = 1L;
        final long orderId = 1L;
        final OrderResponse orderResponse =
            TestJsonUtils.readJson(ORDER_RESPONSE_JSON, OrderResponse.class);

        given(orderService.findById(orderId)).willReturn(orderResponse);

        this.mockMvc.perform(
            RestDocumentationRequestBuilders.get(getUri(shopId, orderId))
        ).andExpect(status().isOk())
            .andDo(print())
            .andDo(document("order-find-by-id",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    @DisplayName("주문 조회 (상태)")
    void findByStatus() throws Exception {
        //given
        final long shopId = 1L;
        final String status = "PENDING";

        final OrderResponse orderResponse1 =
            TestJsonUtils.readJson(ORDER_RESPONSE_JSON, OrderResponse.class);
        final OrderResponse orderResponse2 =
            TestJsonUtils.readJson(ORDER_RESPONSE_JSON, OrderResponse.class);
        final List<OrderResponse> orderResponses = Arrays.asList(orderResponse1, orderResponse2);

        given(orderService.findByStatus(shopId, status)).willReturn(orderResponses);

        // when & then
        this.mockMvc.perform(
            RestDocumentationRequestBuilders.get(getUri(shopId))
                .param("status", "PENDING")
        ).andExpect(status().isOk())
            .andDo(print())
            .andDo(document("order-find-by-status",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    @DisplayName("주문 처리")
    void acceptOrder() throws Exception {
        final long shopId = 1L;
        final long orderId = 1L;

        this.mockMvc.perform(
            RestDocumentationRequestBuilders.put(getUri(shopId, orderId) + "/accept")
        ).andExpect(status().isOk())
            .andDo(print())
            .andDo(document("order-accept",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    @DisplayName("주문 거절")
    void rejectOrder() throws Exception {
        final long shopId = 1L;
        final long orderId = 1L;

        this.mockMvc.perform(
            RestDocumentationRequestBuilders.put(getUri(shopId, orderId) + "/reject")
        ).andExpect(status().isOk())
            .andDo(print())
            .andDo(document("order-reject",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    @DisplayName("주문 완료")
    void finishOrder() throws Exception {
        final long shopId = 1L;
        final long orderId = 1L;

        this.mockMvc.perform(
            RestDocumentationRequestBuilders.put(getUri(shopId, orderId) + "/finish")
        ).andExpect(status().isOk())
            .andDo(print())
            .andDo(document("order-finish",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    private String getUri(final long shopId) {
        return String.format("%s/%d/orders", V1_SHOP, shopId);
    }

    private String getUri(final long shopId, final long orderId) {
        return String.format("%s/%d/orders/%d", V1_SHOP, shopId, orderId);
    }
}
