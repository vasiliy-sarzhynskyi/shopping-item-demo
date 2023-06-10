package com.vsarzhynskyi.shop.items.demo.service;

import com.vsarzhynskyi.shop.items.demo.converter.ShoppingItemConverter;
import com.vsarzhynskyi.shop.items.demo.exception.ShoppingItemNotExistException;
import com.vsarzhynskyi.shop.items.demo.jpa.entity.ShoppingItemEntity;
import com.vsarzhynskyi.shop.items.demo.jpa.repository.ShoppingItemRepository;
import com.vsarzhynskyi.shop.items.demo.model.PageableSearchResult;
import com.vsarzhynskyi.shop.items.demo.model.ShoppingItemModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultShoppingItemService implements ShoppingItemService {

    private final ShoppingItemRepository shopItemRepository;
    private final ShoppingItemConverter shoppingItemConverter;

    @Override
    public Long createShoppingItem(ShoppingItemModel item) {
        var entity = shoppingItemConverter.toEntity(item);
        var savedEntity = shopItemRepository.save(entity);
        log.info("item {} has been successfully created", savedEntity);
        return savedEntity.getId();
    }

    @Override
    public List<Long> bulkCreateShoppingItems(List<ShoppingItemModel> items) {
        var entities = items.stream()
                .map(shoppingItemConverter::toEntity)
                .toList();
        var savedEntities = shopItemRepository.saveAll(entities);
        return savedEntities.stream()
                .map(ShoppingItemEntity::getId)
                .toList();
    }

    @Override
    public void updateShoppingItem(ShoppingItemModel item) {
        var itemId = item.getId();
        var isExistItem = shopItemRepository.existsById(itemId);
        if (!isExistItem) {
            log.error("item with ID = {} not exist for update operation", itemId);
            throw new ShoppingItemNotExistException(itemId);
        }

        var entity = shoppingItemConverter.toEntity(item);
        var savedEntity = shopItemRepository.save(entity);
        log.info("item {} has been successfully updated", savedEntity);
    }

    @Override
    public void deleteShoppingItem(long itemId) {
        var isExistItem = shopItemRepository.existsById(itemId);
        if (!isExistItem) {
            log.error("item with ID = {} not exist for delete operation", itemId);
            throw new ShoppingItemNotExistException(itemId);
        }

        shopItemRepository.deleteById(itemId);
    }

    @Override
    public ShoppingItemModel getShoppingItem(long itemId) {
        var entityOptional = shopItemRepository.findById(itemId);
        if (entityOptional.isEmpty()) {
            log.error("item with ID = {} not exist for fetch operation", itemId);
            throw new ShoppingItemNotExistException(itemId);
        }
        return shoppingItemConverter.toModel(entityOptional.get());
    }

    @Override
    public PageableSearchResult<ShoppingItemModel> getAllShoppingItems(PageRequest pageRequest) {
        var entities = shopItemRepository.findAll(pageRequest);
        var models = entities.stream()
                .map(shoppingItemConverter::toModel)
                .toList();

        return PageableSearchResult.<ShoppingItemModel>builder()
                .data(models)
                .totalPages(entities.getTotalPages())
                .totalElements(entities.getTotalElements())
                .build();
    }

    @Override
    public Optional<ShoppingItemModel> searchShoppingItemByName(String itemName) {
        var entityOptional = shopItemRepository.findByName(itemName);
        return entityOptional
                .map(shoppingItemConverter::toModel);
    }

}
