package com.vsarzhynskyi.shop.items.demo.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class KafkaTopicProperties {

    private Map<String, String> defaultSettings;

    @NotBlank
    private String topicName;

}
