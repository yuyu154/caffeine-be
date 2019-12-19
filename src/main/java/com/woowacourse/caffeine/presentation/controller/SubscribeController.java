package com.woowacourse.caffeine.presentation.controller;

import com.woowacourse.caffeine.application.service.CustomerNotificationService;
import com.woowacourse.caffeine.application.service.ShopNotificationService;
import com.woowacourse.caffeine.application.service.ShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.servlet.http.HttpSession;

import static com.woowacourse.caffeine.presentation.controller.SubscribeController.V1_SUBSCRIBE;

@RestController
@RequestMapping(V1_SUBSCRIBE)
public class SubscribeController {

    public static final String V1_SUBSCRIBE = "/v1/subscribe";

    private final CustomerNotificationService customerNotificationService;
    private final ShopNotificationService shopNotificationService;
    private final ShopService shopService;

    public SubscribeController(final CustomerNotificationService customerNotificationService, final ShopNotificationService shopNotificationService, final ShopService shopService) {
        this.customerNotificationService = customerNotificationService;
        this.shopNotificationService = shopNotificationService;
        this.shopService = shopService;
    }

    @GetMapping("/customers")
    public ResponseBodyEmitter subscribeCustomer(final HttpSession httpSession) {
        String id = httpSession.getId();
        return customerNotificationService.subscribe(id);
    }

    @GetMapping("/shops/{id}")
    public ResponseBodyEmitter subscribeOwner(final @PathVariable Long id) {
        checkExistShop(id);
        return shopNotificationService.subscribe(id);
    }

    private void checkExistShop(@PathVariable Long id) {
        shopService.findById(id);
    }
}
