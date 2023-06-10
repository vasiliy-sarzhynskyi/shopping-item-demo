package com.vsarzhynskyi.shop.items.demo.service;

import com.vsarzhynskyi.shop.items.demo.model.ShoppingItemModel;

import java.util.List;

public interface ShoppingItemCacheableService {

    List<ShoppingItemModel> getAllCachedItems();

}
