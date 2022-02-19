package org.example.middle.process;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.bg.avro.structures.base.objects.CoordinateWithId;

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
        if (kvConsumerRecord.value() instanceof CoordinateWithId) {
            try {
                Thread.sleep(100);
                ProducerRecord<String, CoordinateWithId> record =
                        new ProducerRecord(outputTopic, kvConsumerRecord.key(), kvConsumerRecord.value());
                System.out.println("Middle process, id: " + kvConsumerRecord.key());
                producer.send(record);
            } catch (InterruptedException | SerializationException e) {
                System.out.println("ERROR!!!! " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
