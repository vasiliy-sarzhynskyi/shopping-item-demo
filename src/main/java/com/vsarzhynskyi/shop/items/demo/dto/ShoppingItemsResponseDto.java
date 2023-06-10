package com.vsarzhynskyi.shop.items.demo.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ShoppingItemsResponseDto {

    private List<ShoppingItemResponseDto> items;

}
