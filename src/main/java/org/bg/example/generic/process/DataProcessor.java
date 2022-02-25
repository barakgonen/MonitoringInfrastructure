package org.bg.example.generic.process;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.log4j.Logger;
import org.bg.avro.structures.base.objects.CoordinateWithId;

import java.time.Instant;
import java.util.function.Consumer;

public class DataProcessor<K, V> implements Consumer<ConsumerRecord<K, V>> {
    private static final Logger LOGGER = Logger.getLogger(DataProcessor.class);
    private MyKafkaProducer producer;
    private String outputTopic;
    private final int sleepBetweenSendsMillis;

    public DataProcessor(MyKafkaProducer producer, String outputTopic) {
        this.producer = producer;
        this.outputTopic = outputTopic;
        sleepBetweenSendsMillis = Utils.getEnvIntWithDefalutValue("SLEEP_BETWEEN_SENDS_MILLIS", 100);
    }

    @Override
    public void accept(ConsumerRecord<K, V> kvConsumerRecord) {
        try {
            Thread.sleep(sleepBetweenSendsMillis);
            long nowTimeMillis = Instant.now().toEpochMilli();
            ((CoordinateWithId) kvConsumerRecord.value()).getId().setSendTimeMillis(nowTimeMillis);
            ((CoordinateWithId) kvConsumerRecord.value()).getId().setDiffSinceSendMillis(nowTimeMillis -
                    ((CoordinateWithId) kvConsumerRecord.value()).getId().getSendTimeMillis());
            ProducerRecord<String, CoordinateWithId> record = new ProducerRecord(outputTopic,
                    kvConsumerRecord.key(), kvConsumerRecord.value());
            System.out.println("Middle process, id: " + kvConsumerRecord.key());
            producer.send(record);
            int messageNumber = Integer.parseInt(String.valueOf(kvConsumerRecord.key()));
            if (messageNumber % 100 == 0) {
                LOGGER.info("Sent message number: " + messageNumber);
            }
        } catch (InterruptedException | SerializationException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
