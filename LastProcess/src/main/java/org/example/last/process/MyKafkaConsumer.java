package org.example.last.process;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

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
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put("schema.registry.url", "http://localhost:8081");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return props;
    }

    public void startConsume() {
        try {
            while (true) {
                ConsumerRecords<K, V> records = this.poll(100);
                for (ConsumerRecord<K, V> record : records) {
                    invoker.accept(record);
                }
            }
        } catch (Exception e) {
            System.out.println("Bg: " + e.getMessage());
        } finally{
            this.close();
        }
    }
}
