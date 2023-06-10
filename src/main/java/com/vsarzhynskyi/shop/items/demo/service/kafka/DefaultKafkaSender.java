package com.vsarzhynskyi.shop.items.demo.service.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@AllArgsConstructor
public class DefaultKafkaSender<T> implements KafkaSender<T> {

    private final KafkaTemplate<String, ? super T> kafkaTemplate;

    @Override
    public void sendMessage(T message) {
        log.debug("Sending message [{}] into topic {}", message, kafkaTemplate.getDefaultTopic());
        kafkaTemplate.sendDefault(message);
    }

    @Override
    public void sendMessage(String key, T message) {
        String topic = kafkaTemplate.getDefaultTopic();
        log.debug("Sending message [{}] with key: [{}] into topic {}", message, key, topic);
        kafkaTemplate.sendDefault(key, message);
    }

}
