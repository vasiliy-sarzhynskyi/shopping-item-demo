package com.vsarzhynskyi.shop.items.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ShoppingItemNotExistException extends RuntimeException {

    public ShoppingItemNotExistException(String message) {
        super(message);
    }

    public ShoppingItemNotExistException(long itemId) {
        super(format("Item with ID = %d not exist in database", itemId));
    }

}
