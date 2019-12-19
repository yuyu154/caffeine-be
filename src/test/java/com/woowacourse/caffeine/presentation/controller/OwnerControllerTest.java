package com.woowacourse.caffeine.presentation.controller;

import com.woowacourse.caffeine.application.dto.LoginRequest;
import com.woowacourse.caffeine.application.dto.OwnerResponse;
import com.woowacourse.caffeine.application.dto.SignUpRequest;
import com.woowacourse.caffeine.dbunit.WebTestClientWithDbUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.woowacourse.caffeine.presentation.controller.OwnerController.V1_OWNER;
import static org.assertj.core.api.Assertions.assertThat;

@WebTestClientWithDbUnitTest
public class OwnerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("사장님 회원가입 테스트")
    void signup() {
        signUp();
    }

    @Test
    @DisplayName("사장님 로그인 테스트")
    void login_test() {
        LoginRequest loginRequest = new LoginRequest("kangmin789@naver.com", "p@ssW0rd");
        login(loginRequest);
    }

    @Test
    @DisplayName("사장님 자신 조회 테스트")
    void find() {
        LoginRequest loginRequest = new LoginRequest("kangmin789@naver.com", "p@ssW0rd");

        String jsessionid = login(loginRequest);

        OwnerResponse ownerResponse = webTestClient.get()
            .uri(V1_OWNER + "/me")
            .cookie("JSESSIONID", jsessionid)
            .exchange()
            .expectStatus().isOk()
            .expectBody(OwnerResponse.class).returnResult()
            .getResponseBody();

        assertThat(ownerResponse.getId()).isEqualTo(10L);
        assertThat(ownerResponse.getEmail()).isEqualTo("kangmin789@naver.com");
        assertThat(ownerResponse.getShop()).isNotNull();
    }

    @Test
    @DisplayName("이메일 중복 가입 테스트")
    void duplicate_email() {
        SignUpRequest signUpRequest = new SignUpRequest("kangmin789@naver.com", "p@ssW0rd", "어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)");

        webTestClient.post()
            .uri(V1_OWNER)
            .body(Mono.just(signUpRequest), SignUpRequest.class)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    @DisplayName("로그인 했을 때 로그인 테스트")
    void duplicate_login() {
        LoginRequest loginRequest = new LoginRequest("kangmin789@naver.com", "p@ssW0rd");

        String jsessionid = login(loginRequest);

        webTestClient.post()
            .uri(V1_OWNER + "/login")
            .cookie("JSESSIONID", jsessionid)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("비로그인 로그아웃 테스트")
    void no_signup_logout() {
        webTestClient.get()
            .uri(V1_OWNER + "/logout")
            .exchange()
            .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName("사장님 로그아웃 테스트")
    void logout() {
        LoginRequest loginRequest = new LoginRequest("kangmin789@naver.com", "p@ssW0rd");

        String jsessionid = login(loginRequest);

        webTestClient.get()
            .uri(V1_OWNER + "/logout")
            .cookie("JSESSIONID", jsessionid)
            .exchange()
            .expectStatus().isOk();
    }

    private void signUp() {
        SignUpRequest signUpRequest = new SignUpRequest("kangminals5610@gmail.com", "p@ssW0rd", "어디야 커피 잠실점", "서울특별시 송파구 석촌호수로 262 (송파동)");

        webTestClient.post()
            .uri(V1_OWNER)
            .body(Mono.just(signUpRequest), SignUpRequest.class)
            .exchange()
            .expectStatus().isCreated();
    }

    private String login(LoginRequest loginRequest) {

        return webTestClient.post()
            .uri(V1_OWNER + "/login")
            .body(Mono.just(loginRequest), LoginRequest.class)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .returnResult()
            .getResponseCookies()
            .getFirst("JSESSIONID")
            .getValue();
    }
}
