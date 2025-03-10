package com.engine.test;

import javaslang.collection.List;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

/**
 * @ClassName:MessageQueueUtil
 * @author: qm
 * @Description:
 * @date:2025-02-21
 */
public class MessageQueueUtil {
    private String kafkaServer;
    private String topic;

    public MessageQueueUtil(String kafkaServer, String topic) {
        this.kafkaServer = kafkaServer;
        this.topic = topic;
    }

    public KafkaConsumer<String, String> createConsumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaServer);
        properties.put("group.id", "ruleEngineGroup");
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton(topic));
        return consumer;
    }
}
