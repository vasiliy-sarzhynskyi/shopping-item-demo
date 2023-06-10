package com.vsarzhynskyi.shop.items.demo.controller;

import com.vsarzhynskyi.shop.items.demo.converter.PageableSearchResultConverter;
import com.vsarzhynskyi.shop.items.demo.converter.ShoppingItemConverter;
import com.vsarzhynskyi.shop.items.demo.dto.BulkShoppingItemsRequestDto;
import com.vsarzhynskyi.shop.items.demo.dto.PageableSearchResultDto;
import com.vsarzhynskyi.shop.items.demo.dto.ShoppingItemRequestDto;
import com.vsarzhynskyi.shop.items.demo.dto.ShoppingItemResponseDto;
import com.vsarzhynskyi.shop.items.demo.service.ShoppingItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("shopping-items")
@RequiredArgsConstructor
public class ShoppingItemController {

    private final ShoppingItemService shoppingItemService;
    private final ShoppingItemConverter shoppingItemConverter;
    private final PageableSearchResultConverter pageableSearchResultConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    public Long createShoppingItem(@RequestBody @Valid ShoppingItemRequestDto creationItem) {
        log.info("createShoppingItem {}", creationItem);
        var itemModel = shoppingItemConverter.toModel(creationItem);
        return shoppingItemService.createShoppingItem(itemModel);
    }

    @PostMapping("/bulk-create")
    @ResponseStatus(CREATED)
    public List<Long> bulkCreateShoppingItems(@RequestBody @Valid BulkShoppingItemsRequestDto bulkCreationItems) {
        log.info("bulkCreateShoppingItems {}", bulkCreationItems);
        var itemModels = shoppingItemConverter.toModels(bulkCreationItems);
        return shoppingItemService.bulkCreateShoppingItems(itemModels);
    }

    @PutMapping("/{itemId}")
    public void updateShoppingItem(@PathVariable Long itemId, @RequestBody @Valid ShoppingItemRequestDto updateItem) {
        var itemModel = shoppingItemConverter.toModel(itemId, updateItem);
        shoppingItemService.updateShoppingItem(itemModel);
    }

    @DeleteMapping("/{itemId}")
    public void deleteShoppingItem(@PathVariable Long itemId) {
        shoppingItemService.deleteShoppingItem(itemId);
    }

    @GetMapping("/{itemId}")
    public ShoppingItemResponseDto getShoppingItem(@PathVariable Long itemId) {
        var itemModel = shoppingItemService.getShoppingItem(itemId);
        return shoppingItemConverter.toDto(itemModel);
    }

    @GetMapping("/search/by-name")
    public Optional<ShoppingItemResponseDto> searchShoppingItemByName(@RequestParam(value = "name") String itemName) {
        var itemModel = shoppingItemService.searchShoppingItemByName(itemName);
        return itemModel
                .map(shoppingItemConverter::toDto);
    }

    @GetMapping
    public PageableSearchResultDto<ShoppingItemResponseDto> getAllShoppingItems(@RequestParam("page") @Min(0) int page, @RequestParam("size") @Min(1) int size) {
        var pageableItems = shoppingItemService.getAllShoppingItems(PageRequest.of(page,size));
        return pageableSearchResultConverter.toDto(pageableItems, shoppingItemConverter::toDto);
    }

}
