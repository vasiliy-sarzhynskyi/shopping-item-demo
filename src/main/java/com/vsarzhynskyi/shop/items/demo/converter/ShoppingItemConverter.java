package com.vsarzhynskyi.shop.items.demo.converter;

import com.vsarzhynskyi.shop.items.demo.dto.BulkShoppingItemsRequestDto;
import com.vsarzhynskyi.shop.items.demo.dto.ShoppingItemRequestDto;
import com.vsarzhynskyi.shop.items.demo.dto.ShoppingItemResponseDto;
import com.vsarzhynskyi.shop.items.demo.dto.ShoppingItemsResponseDto;
import com.vsarzhynskyi.shop.items.demo.jpa.entity.ShoppingItemEntity;
import com.vsarzhynskyi.shop.items.demo.model.ShoppingItemModel;

import java.util.List;

public interface ShoppingItemConverter {

    ShoppingItemModel toModel(ShoppingItemRequestDto dto);

    List<ShoppingItemModel> toModels(BulkShoppingItemsRequestDto dto);

    ShoppingItemModel toModel(Long itemId, ShoppingItemRequestDto dto);

    ShoppingItemModel toModel(ShoppingItemEntity entity);

    ShoppingItemResponseDto toDto(ShoppingItemModel model);

    ShoppingItemEntity toEntity(ShoppingItemModel model);

    ShoppingItemsResponseDto toDto(List<ShoppingItemModel> models);

}
