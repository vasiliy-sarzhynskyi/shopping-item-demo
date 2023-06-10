package com.vsarzhynskyi.shop.items.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties("kafka")
public class KafkaConfigurationProperties {

    private Map<String, String> defaultSettings = new HashMap<>();

    private KafkaTopicProperties httpRequestTraceReport;

}
