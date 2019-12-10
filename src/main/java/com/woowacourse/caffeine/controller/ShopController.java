package com.woowacourse.caffeine.controller;

import com.woowacourse.caffeine.application.dto.MenuItemResponse;
import com.woowacourse.caffeine.application.dto.ShopCreateRequest;
import com.woowacourse.caffeine.application.dto.ShopResponse;
import com.woowacourse.caffeine.application.dto.ShopResponses;
import com.woowacourse.caffeine.application.service.MenuItemService;
import com.woowacourse.caffeine.application.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ShopController.V1_SHOP)
public class ShopController {

    public static final String V1_SHOP = "/v1/shops";
    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private final ShopService shopService;
    private final MenuItemService menuItemService;

    public ShopController(final ShopService shopService, final MenuItemService menuItemService) {
        this.shopService = shopService;
        this.menuItemService = menuItemService;
    }

    @PostMapping
    public ResponseEntity createShop(@RequestBody final ShopCreateRequest request) {
        ShopResponse createdShop = shopService.createShop(request);
        logger.debug("Create Shop {}", createdShop);

        return ResponseEntity.created(URI.create(V1_SHOP + "/" + createdShop.getId()))
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity retrieveShop(@PathVariable final long id) {
        ShopResponse foundedShopResponse = shopService.findById(id);
        logger.debug("Founded ShopResponse: {}", foundedShopResponse);

        return ResponseEntity.ok(foundedShopResponse);
    }

    @GetMapping("/{id}/menus")
    public ResponseEntity retrieveMenus(@PathVariable final long id) {
        List<MenuItemResponse> menuItemResponses = menuItemService.findByShopId(id);
        logger.debug("Menus Of Shop({}) : {}", menuItemResponses, id);

        return ResponseEntity.ok(menuItemResponses);
    }

    @GetMapping
    public ResponseEntity findAllShops() {
        ShopResponses shopResponses = shopService.findAll();
        logger.debug("ShopResponses: {}", shopResponses);

        return ResponseEntity.ok(shopResponses);
    }
}
