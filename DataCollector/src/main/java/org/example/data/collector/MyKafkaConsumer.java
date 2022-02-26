package org.example.data.collector;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Consumer;

public class MyKafkaConsumer<K, V> extends KafkaConsumer<K, V> {

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
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.40:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put("schema.registry.url", "http://192.168.1.40:8081");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
//        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        return props;
    }

    public void startConsume() {
        try {
            while (true) {
                ConsumerRecords<K, V> records = this.poll(Duration.of(2000, ChronoUnit.MILLIS));
//                for (ConsumerRecord<K, V> record : records) {
//                    System.out.printf("offset = %d, key = %s, value = %s, record timestamp = %s \n", record.offset(), record.key(), record.value(), record.timestamp());
                    if (invoker != null && !records.isEmpty())
                        invoker.accept(records);
                }
        } catch (Exception e) {
            System.out.println("E: " + e.getMessage());
        } finally {
            this.close();
        }
    }
}
