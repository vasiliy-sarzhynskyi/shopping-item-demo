package com.vsarzhynskyi.shop.items.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ShoppingItemRequestDto {

    @NotBlank
    private String name;

    private String description;

    private Long version;

}
