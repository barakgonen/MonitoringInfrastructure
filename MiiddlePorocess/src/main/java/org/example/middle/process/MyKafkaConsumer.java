package org.example.middle.process;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;
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
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group7");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        props.put("schema.registry.url", "http://localhost:8081");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    public void startConsume() {
        try {
            while (true) {
                ConsumerRecords<K, V> records = this.poll(100);
                for (ConsumerRecord<K, V> record : records) {
//                    System.out.printf("offset = %d, key = %s, value = %s, record timestamp = %s \n", record.offset(), record.key(), record.value(), record.timestamp());
                    invoker.accept(record);
                }
            }
        } finally {
            this.close();
        }
    }
}
