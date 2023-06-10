package com.vsarzhynskyi.shop.items.demo.model;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class ShoppingItemModel {

    private Long id;

    private String name;

    private String description;

    private Instant createdTimestamp;

    private Instant updatedTimestamp;

    private Long version;

}
