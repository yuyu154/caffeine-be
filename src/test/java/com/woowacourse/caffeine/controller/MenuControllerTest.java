package com.woowacourse.caffeine.controller;

import com.woowacourse.caffeine.application.dto.MenuCreateRequest;
import com.woowacourse.caffeine.application.dto.MenuItemUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.woowacourse.caffeine.controller.MenuController.V1_MENU;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class MenuControllerTest {

    private static final long DEFAULT_SHOP_ID = 100L;
    private static final long DEFAULT_MENU_ID = 987654321L;
    private static final long UPDATE_MENU_ID = 987654322L;
    private static final long DELETE_MENU_ID = 987654323L;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("메뉴 생성")
    void createMenu() {
        // given
        String menuName = "아메리카노";
        String menuNameInEnglish = "Americano";
        String description = "맛있는 아메리카노";
        int price = 3000;
        String imgUrl = "abc";
        String category = "coffee";

        MenuCreateRequest menuCreateRequest = new MenuCreateRequest(menuName, menuNameInEnglish, description, price, imgUrl, category, DEFAULT_SHOP_ID);

        // when
        EntityExchangeResult<byte[]> response = webTestClient.post()
            .uri(V1_MENU)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(menuCreateRequest), MenuCreateRequest.class).exchange()
            .expectStatus().isCreated()
            .expectHeader().valueMatches("Location", V1_MENU + "/\\d*")
            .expectBody().returnResult();

        // then
        webTestClient.get().uri(response.getResponseHeaders().getLocation().toASCIIString())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.name").isEqualTo(menuName)
            .jsonPath("$.description").isEqualTo(description)
            .jsonPath("$.price").isEqualTo(price);
    }

    @Test
    @DisplayName("메뉴 조회")
    void find_menu_by_menuId() {

        //given
        String name = "카페라떼";
        String description = "고소한 카페라떼";
        int price = 3000;

        // when & then
        webTestClient.get()
            .uri(String.format("%s/%d", V1_MENU, DEFAULT_MENU_ID))
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(DEFAULT_MENU_ID)
            .jsonPath("$.name").isEqualTo(name)
            .jsonPath("$.description").isEqualTo(description)
            .jsonPath("$.price").isEqualTo(price);
    }

    @Test
    @DisplayName("메뉴 정보 수정")
    void update_menu_item() {
        //given
        String updatedName = "아이스 고구마라떼";
        String updatedMenuNameInEnglish = "Americano";
        String updatedDescription = "시원한 고구마라떼";
        String updatedImg = "abc";
        int updatedPrice = 4000;
        String updatedCategory = "coffee";

        MenuItemUpdateRequest menuUpdateRequest = new MenuItemUpdateRequest(
            updatedName, updatedMenuNameInEnglish, updatedDescription, updatedPrice, updatedImg, updatedCategory);

        // when
        webTestClient.get()
            .uri(String.format("%s/%d", V1_MENU, UPDATE_MENU_ID))
            .exchange()
            .expectBody()
            .jsonPath("$.name").isEqualTo("고구마라떼")
            .jsonPath("$.description").isEqualTo("달달한 라떼")
            .jsonPath("$.price").isEqualTo(3000);

        webTestClient.put()
            .uri(String.format("%s/%d", V1_MENU, UPDATE_MENU_ID))
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(menuUpdateRequest), MenuItemUpdateRequest.class)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo(updatedName)
            .jsonPath("$.nameInEnglish").isEqualTo(updatedMenuNameInEnglish)
            .jsonPath("$.description").isEqualTo(updatedDescription)
            .jsonPath("$.imgUrl").isEqualTo("abc")
            .jsonPath("$.price").isEqualTo(updatedPrice)
            .jsonPath("$.category").isEqualTo("coffee");

        // then
        webTestClient.get()
            .uri(String.format("%s/%d", V1_MENU, UPDATE_MENU_ID))
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo(updatedName)
            .jsonPath("$.nameInEnglish").isEqualTo(updatedMenuNameInEnglish)
            .jsonPath("$.description").isEqualTo(updatedDescription)
            .jsonPath("$.imgUrl").isEqualTo("abc")
            .jsonPath("$.price").isEqualTo(updatedPrice)
            .jsonPath("$.category").isEqualTo("coffee");
    }

    @Test
    @DisplayName("메뉴 삭제")
    void delete_menu() {

        // when
        webTestClient.delete()
            .uri(String.format("%s/%d", V1_MENU, DELETE_MENU_ID))
            .exchange()
            .expectStatus().isNoContent();

        // then
        webTestClient.get()
            .uri(String.format("%s/%d", V1_MENU, DELETE_MENU_ID))
            .exchange()
            .expectStatus().is5xxServerError();
    }

}
