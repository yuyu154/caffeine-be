package com.woowacourse.caffeine.presentation.controller;

import com.woowacourse.caffeine.application.dto.LoginRequest;
import com.woowacourse.caffeine.application.dto.OwnerResponse;
import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.SignUpRequest;
import com.woowacourse.caffeine.application.exception.DuplicateLoginException;
import com.woowacourse.caffeine.application.exception.SessionValueNotFoundException;
import com.woowacourse.caffeine.application.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.URI;

import static com.woowacourse.caffeine.presentation.controller.OwnerController.V1_OWNER;

@RestController
@RequestMapping(V1_OWNER)
public class OwnerController {

    public static final String DEFAULT_IMAGE =
        "https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/" +
            "starbucks_%EC%84%9D%EC%B4%8C%ED%98%B8%EC%88%98.jpg?raw=true";
    public static final String DEFAULT_PHONE_NUMBER = "010-3080-5610";
    public static final String V1_OWNER = "/v1/owners";
    private static final String SESSION_KEY = "email";

    private final OwnerService ownerService;

    public OwnerController(final OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    public ResponseEntity signUpAndCreateShop(@RequestBody final SignUpRequest signUpRequest) {
        ShopCreateRequest shopCreateRequest = new ShopCreateRequest(signUpRequest.getShopName(), DEFAULT_IMAGE, signUpRequest.getShopAddress(), DEFAULT_PHONE_NUMBER);
        Long id = ownerService.signUpAndCreateShop(signUpRequest, shopCreateRequest);
        return ResponseEntity.created(URI.create(V1_OWNER + "/" + id)).build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody final LoginRequest loginRequest, final HttpSession httpSession) {
        if (httpSession.getAttribute(SESSION_KEY) != null) {
            throw new DuplicateLoginException();
        }
        String email = ownerService.authenticate(loginRequest);
        httpSession.setAttribute(SESSION_KEY, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity find(final HttpSession httpSession) {
        String email = getSessionEmailIfExist(httpSession);
        OwnerResponse ownerResponse = ownerService.findByEmail(email);
        return ResponseEntity.ok(ownerResponse);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(final HttpSession httpSession) {
        getSessionEmailIfExist(httpSession);
        httpSession.removeAttribute(SESSION_KEY);
        return ResponseEntity.ok().build();
    }

    private String getSessionEmailIfExist(final HttpSession httpSession) {
        String email = (String) httpSession.getAttribute(SESSION_KEY);
        if (email == null) {
            throw new SessionValueNotFoundException();
        }
        return email;
    }
}
