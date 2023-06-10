package com.vsarzhynskyi.shop.items.demo.service;

import com.vsarzhynskyi.shop.items.demo.configuration.CacheConfiguration;
import com.vsarzhynskyi.shop.items.demo.converter.ShoppingItemConverter;
import com.vsarzhynskyi.shop.items.demo.jpa.repository.ShoppingItemRepository;
import com.vsarzhynskyi.shop.items.demo.model.ShoppingItemModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultShoppingItemCacheableService implements ShoppingItemCacheableService {

    private final ShoppingItemRepository shopItemRepository;
    private final ShoppingItemConverter shoppingItemConverter;

    @Override
    @Cacheable(cacheNames = CacheConfiguration.GET_ALL_SHOPPING_ITEMS_CACHE_KEY, sync = true)
    public List<ShoppingItemModel> getAllCachedItems() {
        log.info("reinitializing cached data for getAllCachedItems");
        var entities = shopItemRepository.findAll();
        return entities.stream()
                .map(shoppingItemConverter::toModel)
                .toList();
    }

}
