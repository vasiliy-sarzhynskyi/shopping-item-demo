package com.vsarzhynskyi.shop.items.demo.functionaltests;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class FunctionalTestConfiguration {

    @Value("${embedded.kafka.brokerList}")
    private String kafkaBrokerList;

    @Value("${kafka.httpRequestTraceReport.topicName}")
    private String shoppingItemHttpTraceReportingTopicName;


    @Bean(destroyMethod = "close")
    ShoppingItemHttpTraceReportingKafkaConsumer shoppingItemHttpTraceReportingKafkaConsumer() {
        return new ShoppingItemHttpTraceReportingKafkaConsumer(shoppingItemHttpTraceReportingTopicName, kafkaBrokerList);
    }

}
