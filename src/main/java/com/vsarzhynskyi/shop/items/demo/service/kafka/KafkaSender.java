package com.vsarzhynskyi.shop.items.demo.service.kafka;

public interface KafkaSender<T> {

    void sendMessage(T message);

    void sendMessage(String key, T message);

}
