package com.vsarzhynskyi.shop.items.demo.functionaltests;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.awaitility.Awaitility.await;
import static org.springframework.kafka.test.utils.KafkaTestUtils.getRecords;

@Slf4j
public class ShoppingItemHttpTraceReportingKafkaConsumer implements Closeable {

    private static final int KAFKA_POLL_TIMEOUT_MS = 2000;
    private static final int AWAITILITY_TIMEOUT_MS = 5000;

    private final KafkaConsumer<String, String> consumer;

    public ShoppingItemHttpTraceReportingKafkaConsumer(String topic, String brokers) {
        consumer = createKafkaConsumer(topic, brokers);
    }

    public List<String> waitAndReceiveRecords(int maxMessages) {
        var fetchedMessages = new ArrayList<String>();
        await().atMost(AWAITILITY_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> {
                    var records = getRecords(consumer, Duration.of(KAFKA_POLL_TIMEOUT_MS, ChronoUnit.MILLIS));
                    records.forEach(record -> {
                        fetchedMessages.add(String.valueOf(record.value()));
                    });

                    assert fetchedMessages.size() >= maxMessages;
                });
        log.info("fetched the following Kafka messages: {}", fetchedMessages);
        return fetchedMessages;
    }

    @Override
    public void close() {
        consumer.close();
    }

    private KafkaConsumer<String, String> createKafkaConsumer(String topic, String brokers) {
        var config = Map.<String, Object>of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers,
                ConsumerConfig.GROUP_ID_CONFIG, format("test-group[%s]", topic),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 0
        );

        var kafkaConsumer = new KafkaConsumer<String, String>(config);
        kafkaConsumer.subscribe(List.of(topic));
        return kafkaConsumer;
    }

}
