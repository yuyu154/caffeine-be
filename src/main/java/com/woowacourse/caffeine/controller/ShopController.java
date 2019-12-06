package com.woowacourse.caffeine.controller;

import com.woowacourse.caffeine.application.service.MenuItemService;
import com.woowacourse.caffeine.application.service.ShopService;
import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(ShopController.V1_SHOP)
public class ShopController {

    public static final String V1_SHOP = "/v1/shop";

    private final ShopService shopService;
    private final MenuItemService menuItemService;

    public ShopController(final ShopService shopService, final MenuItemService menuItemService) {
        this.shopService = shopService;
        this.menuItemService = menuItemService;
    }

    @PostMapping
    public ResponseEntity createShop(@RequestBody final ShopCreateRequest request) {
        ShopResponse createdShop = shopService.createShop(request);

        return ResponseEntity.created(URI.create(V1_SHOP + "/" + createdShop.id))
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity retrieveShop(@PathVariable final long id) {
        ShopResponse found = shopService.findById(id);

        return ResponseEntity.ok(found);
    }

    @GetMapping("/{id}/menus")
    public ResponseEntity retrieveMenus(@PathVariable final long id) {
        return ResponseEntity.ok(menuItemService.findByShopId(id));
    }
}
