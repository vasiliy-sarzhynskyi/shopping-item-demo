package com.vsarzhynskyi.shop.items.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class BulkShoppingItemsRequestDto {

    @NotEmpty
    private List<ShoppingItemRequestDto> items;

}
