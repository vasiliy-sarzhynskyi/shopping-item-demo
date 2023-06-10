package com.vsarzhynskyi.shop.items.demo.service;

import com.vsarzhynskyi.shop.items.demo.converter.ShoppingItemConverter;
import com.vsarzhynskyi.shop.items.demo.jpa.entity.ShoppingItemEntity;
import com.vsarzhynskyi.shop.items.demo.jpa.repository.ShoppingItemRepository;
import com.vsarzhynskyi.shop.items.demo.model.ShoppingItemModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultShoppingItemServiceTest {

    private ShoppingItemRepository shopItemRepository = Mockito.mock(ShoppingItemRepository.class);
    private ShoppingItemConverter shoppingItemConverter = Mockito.mock(ShoppingItemConverter.class);
    private ShoppingItemService shoppingItemService = new DefaultShoppingItemService(shopItemRepository, shoppingItemConverter);

    @Test
    void testCreateNewShoppingItem() {
        var shoppingItemModel = ShoppingItemModel.builder()
                .name("test name")
                .description("test description")
                .build();
        var expectedEntity = ShoppingItemEntity.builder()
                .name("test name")
                .description("test description")
                .build();
        var expectedSavedEntity = expectedEntity.toBuilder()
                .id(1L)
                .build();

        when(shoppingItemConverter.toEntity(shoppingItemModel))
                .thenReturn(expectedEntity);
        when(shopItemRepository.save(expectedEntity))
                .thenReturn(expectedSavedEntity);

        var savedItemIdResult = shoppingItemService.createShoppingItem(shoppingItemModel);
        assertEquals(1L, savedItemIdResult);

        verify(shoppingItemConverter).toEntity(shoppingItemModel);
        verify(shopItemRepository).save(expectedEntity);
    }

}
