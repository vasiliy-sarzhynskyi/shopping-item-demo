package com.vsarzhynskyi.shop.items.demo.converter;

import com.vsarzhynskyi.shop.items.demo.dto.BulkShoppingItemsRequestDto;
import com.vsarzhynskyi.shop.items.demo.dto.ShoppingItemRequestDto;
import com.vsarzhynskyi.shop.items.demo.dto.ShoppingItemResponseDto;
import com.vsarzhynskyi.shop.items.demo.dto.ShoppingItemsResponseDto;
import com.vsarzhynskyi.shop.items.demo.jpa.entity.ShoppingItemEntity;
import com.vsarzhynskyi.shop.items.demo.model.ShoppingItemModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultShoppingItemConverter implements ShoppingItemConverter {

    @Override
    public ShoppingItemModel toModel(ShoppingItemRequestDto dto) {
        return ShoppingItemModel.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .version(dto.getVersion())
                .build();
    }

    @Override
    public List<ShoppingItemModel> toModels(BulkShoppingItemsRequestDto dto) {
        return dto.getItems().stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public ShoppingItemModel toModel(Long itemId, ShoppingItemRequestDto dto) {
        return toModel(dto).toBuilder()
                .id(itemId)
                .build();

    }

    @Override
    public ShoppingItemModel toModel(ShoppingItemEntity entity) {
        return ShoppingItemModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdTimestamp(entity.getCreatedTimestamp())
                .updatedTimestamp(entity.getUpdatedTimestamp())
                .version(entity.getVersion())
                .build();
    }

    @Override
    public ShoppingItemResponseDto toDto(ShoppingItemModel model) {
        return ShoppingItemResponseDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .createdTimestamp(model.getCreatedTimestamp().toEpochMilli())
                .updatedTimestamp(model.getUpdatedTimestamp().toEpochMilli())
                .version(model.getVersion())
                .build();
    }

    @Override
    public ShoppingItemEntity toEntity(ShoppingItemModel model) {
        return ShoppingItemEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .createdTimestamp(model.getCreatedTimestamp())
                .updatedTimestamp(model.getUpdatedTimestamp())
                .version(model.getVersion())
                .build();
    }

    @Override
    public ShoppingItemsResponseDto toDto(List<ShoppingItemModel> models) {
        var itemDtos = models.stream()
                .map(this::toDto)
                .toList();
        return ShoppingItemsResponseDto.builder()
                .items(itemDtos)
                .build();
    }

}
