package org.bg.example.generic.process;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Consumer;

public class MyKafkaConsumer<K, V> extends KafkaConsumer<K, V> {

    private Consumer<ConsumerRecord<K, V>> invoker;

    public MyKafkaConsumer(Consumer<ConsumerRecord<K, V>> consumer) {
        super(getProps());
        this.invoker = consumer;
    }

    private static Properties getProps() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Utils.getEnvString("KAFKA_ADDRESS"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        props.put("schema.registry.url", Utils.getEnvString("SCHEMA_REGISTRY_URL"));
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return props;
    }

    public void startConsume() {
        try {
            while (true) {
                ConsumerRecords<K, V> records = this.poll(Duration.of(50, ChronoUnit.MILLIS));
                for (ConsumerRecord<K, V> record : records) {
                    invoker.accept(record);
                }
            }
        } finally {
            this.close();
        }
    }
}
