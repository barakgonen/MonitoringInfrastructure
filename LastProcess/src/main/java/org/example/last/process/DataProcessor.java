package org.example.last.process;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.bg.avro.structures.base.objects.CoordinateWithId;

import java.time.Instant;
import java.util.function.Consumer;

public class DataProcessor<K, V> implements Consumer<ConsumerRecord<K, V>> {
    private MyKafkaProducer producer;
    private String outputTopic;

    public DataProcessor(MyKafkaProducer producer, String outputTopic) {
        this.producer = producer;
        this.outputTopic = outputTopic;
    }

    @Override
    public void accept(ConsumerRecord<K, V> kvConsumerRecord) {
        try {
            System.out.println("Last process, id: " + kvConsumerRecord.key() + ", total time: " + (Instant.now().toEpochMilli() - ((CoordinateWithId)kvConsumerRecord.value()).getId().getStartTimeMillis()) + " millis");
            ProducerRecord<String, CoordinateWithId> record =
                    new ProducerRecord(outputTopic, kvConsumerRecord.key(), kvConsumerRecord.value());
            producer.send(record);
        } catch (SerializationException e) {
            System.out.println("ERROR!!!! " + e.getMessage());
            e.printStackTrace();
        }
    }
}
