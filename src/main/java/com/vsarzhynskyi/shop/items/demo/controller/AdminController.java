package com.vsarzhynskyi.shop.items.demo.controller;

import com.vsarzhynskyi.shop.items.demo.converter.ShoppingItemConverter;
import com.vsarzhynskyi.shop.items.demo.dto.ShoppingItemsResponseDto;
import com.vsarzhynskyi.shop.items.demo.service.ShoppingItemCacheableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final ShoppingItemCacheableService shoppingItemCacheableService;
    private final ShoppingItemConverter shoppingItemConverter;

    @GetMapping("/cached-items")
    public ShoppingItemsResponseDto getAllCachedShoppingItems() {
        var itemModels = shoppingItemCacheableService.getAllCachedItems();
        var itemDtos = itemModels.stream()
                .map(shoppingItemConverter::toDto)
                .toList();
        return ShoppingItemsResponseDto.builder()
                .items(itemDtos)
                .build();
    }

}
