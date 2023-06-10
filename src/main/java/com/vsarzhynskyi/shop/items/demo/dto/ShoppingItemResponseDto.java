package com.vsarzhynskyi.shop.items.demo.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ShoppingItemResponseDto {

    private Long id;

    private String name;

    private String description;

    private Long createdTimestamp;

    private Long updatedTimestamp;

    private Long version;

}
