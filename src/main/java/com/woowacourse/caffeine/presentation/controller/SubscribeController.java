package com.woowacourse.caffeine.presentation.controller;

import com.woowacourse.caffeine.application.dto.NotificationSubscriptionsRequest;
import com.woowacourse.caffeine.application.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.woowacourse.caffeine.presentation.controller.SubscribeController.V1_SUBSCRIBE;

@RestController
@RequestMapping(V1_SUBSCRIBE)
public class SubscribeController {

    public static final String V1_SUBSCRIBE = "/v1/subscribe";

    private final NotificationService notificationService;

    public SubscribeController(final NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/customers")
    public ResponseEntity subscribeCustomer(final HttpSession httpSession, @RequestBody final NotificationSubscriptionsRequest request) {
        String id = httpSession.getId();
        notificationService.subscribeCustomer(id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/shops/{id}")
    public ResponseEntity subscribeOwner(final @PathVariable Long id, @RequestBody final NotificationSubscriptionsRequest request) {
        notificationService.subscribeShop(id, request);
        return ResponseEntity.ok().build();
    }
}
