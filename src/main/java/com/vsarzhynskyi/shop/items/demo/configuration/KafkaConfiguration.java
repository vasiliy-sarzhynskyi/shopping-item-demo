package com.vsarzhynskyi.shop.items.demo.configuration;

import com.vsarzhynskyi.shop.items.demo.dto.HttpRequestTraceReportDto;
import com.vsarzhynskyi.shop.items.demo.properties.KafkaConfigurationProperties;
import com.vsarzhynskyi.shop.items.demo.service.kafka.DefaultKafkaSender;
import com.vsarzhynskyi.shop.items.demo.service.kafka.KafkaSender;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(KafkaConfigurationProperties.class)
public class KafkaConfiguration {

    @Bean
    public KafkaTemplate<String, Object> httpRequestTraceReportKafkaTemplate(ProducerFactory<String, Object> producerFactory,
                                                                             KafkaConfigurationProperties kafkaConfigurationProperties) {
        String topicName = kafkaConfigurationProperties.getHttpRequestTraceReport().getTopicName();
        return createKafkaTemplate(producerFactory, topicName);
    }

    @Bean
    public KafkaSender<HttpRequestTraceReportDto> httpRequestTraceReportKafkaSender(KafkaTemplate<String, Object> httpRequestTraceReportKafkaTemplate) {
        return new DefaultKafkaSender<>(httpRequestTraceReportKafkaTemplate);
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory(KafkaConfigurationProperties kafkaConfigurationProperties) {
        return new DefaultKafkaProducerFactory<>(producerConfigs(kafkaConfigurationProperties.getDefaultSettings()));
    }

    private static <T> KafkaTemplate<String, T> createKafkaTemplate(ProducerFactory<String, T> producerFactory,
                                                                    String defaultTopicName) {
        KafkaTemplate<String, T> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setDefaultTopic(defaultTopicName);
        return kafkaTemplate;
    }

    private static Map<String, Object> producerConfigs(Map<String, String> defaultSettings) {
        Map<String, Object> props = new HashMap<>(defaultSettings);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

}
