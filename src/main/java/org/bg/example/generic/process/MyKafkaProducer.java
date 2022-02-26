package org.bg.example.generic.process;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class MyKafkaProducer<K, V> extends KafkaProducer<K, V> {
    public MyKafkaProducer() {
        super(getProps());
    }

    private static Properties getProps() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Utils.getEnvString("KAFKA_ADDRESS"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        props.put("schema.registry.url", Utils.getEnvString("SCHEMA_REGISTRY_URL"));
        return props;
    }
}
