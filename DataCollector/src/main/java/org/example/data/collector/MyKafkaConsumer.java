package org.example.data.collector;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Consumer;

import static org.example.data.collector.Constants.KAFKA_ADDRESS;
import static org.example.data.collector.Constants.SCHEMA_REGISTRY_URL;

public class MyKafkaConsumer<K, V> extends KafkaConsumer<K, V> {
    private static final Logger LOGGER = Logger.getLogger(MyKafkaConsumer.class);
    private Consumer<ConsumerRecords<K, V>> invoker;

    public MyKafkaConsumer(Consumer<ConsumerRecords<K, V>> consumer) {
        super(getProps());
        this.invoker = consumer;
    }

    public MyKafkaConsumer() {
        super(getProps());
    }

    private static Properties getProps() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_ADDRESS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put("schema.registry.url", SCHEMA_REGISTRY_URL);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put("replica.fetch.min.bytes", 5);
        props.put("replica.fetch.wait.max.ms", 5000);
        return props;
    }

    public void startConsume() {
        int numberOfEmptyPollsToExit = 50;
        while (numberOfEmptyPollsToExit > 0) {
            ConsumerRecords<K, V> records = this.poll(Duration.of(5, ChronoUnit.SECONDS));
            if (records.isEmpty()) {
                numberOfEmptyPollsToExit--;
            } else if (invoker != null){
                invoker.accept(records);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        close();
    }
}
