package com.vsarzhynskyi.shop.items.demo.service;

import com.vsarzhynskyi.shop.items.demo.model.PageableSearchResult;
import com.vsarzhynskyi.shop.items.demo.model.ShoppingItemModel;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface ShoppingItemService {

    Long createShoppingItem(ShoppingItemModel item);
    List<Long> bulkCreateShoppingItems(List<ShoppingItemModel> items);
    void updateShoppingItem(ShoppingItemModel item);
    void deleteShoppingItem(long itemId);
    ShoppingItemModel getShoppingItem(long itemId);

    PageableSearchResult<ShoppingItemModel> getAllShoppingItems(PageRequest pageRequest);

    Optional<ShoppingItemModel> searchShoppingItemByName(String itemName);

}
