package com.vsarzhynskyi.shop.items.demo.service.time;

import org.springframework.stereotype.Component;

@Component
public class DefaultTimeProvider implements TimeProvider {

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

}
