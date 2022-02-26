package org.bg.example.generic.process;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.log4j.Logger;
import org.bg.avro.structures.base.objects.CoordinateWithId;

public class ProducerAction implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ProducerAction.class);
    private final int numberOfMessagesToProduce;
    private final String outputTopic;
    private final int sleepBetweenSendsMillis;

    public ProducerAction() {
        numberOfMessagesToProduce = Utils.getEnvInt("NUMBER_OF_MESSAGES_TO_PRODUCE");
        outputTopic = Utils.getTopicToProduceTo();
        sleepBetweenSendsMillis = Utils.getEnvIntWithDefalutValue("SLEEP_BETWEEN_SENDS_MILLIS", 100);
        LOGGER.setLevel(Utils.getEnvLogLevel());
    }

    private boolean isInputValid() {
        if (numberOfMessagesToProduce == -1) {
            LOGGER.error("Please enter valid number of messages to produce in ENV called: " +
                    "NUMBER_OF_MESSAGES_TO_PRODUCE");
            return false;
        }
        if (outputTopic == null) {
            LOGGER.error("Please enter valid output topic to produce to in ENV called: " +
                    "PRODUCE_TO_TOPIC");
            return false;
        }

        return true;
    }

    @Override
    public void run() {
        if (isInputValid()) {
            MyKafkaProducer kafkaProducer = new MyKafkaProducer();
            for (int i = 0; i < numberOfMessagesToProduce; i++) {
                try {
                    ProducerRecord<String, CoordinateWithId> record = new ProducerRecord<>(outputTopic,
                            String.valueOf(i), DataGenerator.generateData());
                    LOGGER.debug("Sent message number: " + i);
                    if (i % 100 == 0) {
                        LOGGER.info("Sent message number: " + i);
                    }
                    kafkaProducer.send(record);
                    Thread.sleep(sleepBetweenSendsMillis);
                } catch (InterruptedException | SerializationException e) {
                    LOGGER.error(e.getMessage());
                }
            }
            kafkaProducer.flush();
            kafkaProducer.close();
            LOGGER.info("Finished sending: " + numberOfMessagesToProduce + " messages");
        }
    }
}
