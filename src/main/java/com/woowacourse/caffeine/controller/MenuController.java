package com.woowacourse.caffeine.controller;

import com.woowacourse.caffeine.application.dto.MenuCreateRequest;
import com.woowacourse.caffeine.application.dto.MenuItemResponse;
import com.woowacourse.caffeine.application.dto.MenuItemUpdateRequest;
import com.woowacourse.caffeine.application.service.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(MenuController.V1_MENU)
public class MenuController {

    public static final String V1_MENU = "/v1/menus";
    private final MenuItemService menuItemService;

    public MenuController(final MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    public ResponseEntity createMenuItem(@RequestBody MenuCreateRequest menuCreateRequest) {
        MenuItemResponse menuItemResponse = menuItemService.createMenuItem(menuCreateRequest);
        return ResponseEntity.created(URI.create(V1_MENU + "/" + menuItemResponse.getId()))
            .build();
    }

    @GetMapping("/{menuItemId}")
    public ResponseEntity findMenuItem(@PathVariable final long menuItemId) {
        MenuItemResponse menuItemResponse = menuItemService.findByMenuItemId(menuItemId);
        return ResponseEntity.ok(menuItemResponse);
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity updateMenuItem(@PathVariable final long menuItemId, @RequestBody MenuItemUpdateRequest menuItemUpdateRequest) {
        MenuItemResponse menuItemResponse = menuItemService.updateMenuItem(menuItemId, menuItemUpdateRequest);
        return ResponseEntity.ok(menuItemResponse);
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity deleteMenuItem(@PathVariable final long menuItemId) {
        menuItemService.deleteMenuItem(menuItemId);
        return ResponseEntity.noContent().build();
    }
}
