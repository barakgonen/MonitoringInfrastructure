package org.bg.example.generic.process;

import java.util.List;

public class ConsumerAndProducerAction implements Runnable {
    private final String topicToConsumeFrom;
    private final String topicToProduceTo;
    private final MyKafkaProducer kafkaProducer;
    private final DataProcessor dataProcessor;
    private final MyKafkaConsumer kafkaConsumer;


    public ConsumerAndProducerAction() {
        topicToConsumeFrom = Utils.getTopicToConsumeFrom();
        topicToProduceTo = Utils.getTopicToProduceTo();
        kafkaProducer = new MyKafkaProducer();
        dataProcessor = new DataProcessor(kafkaProducer, topicToProduceTo);
        kafkaConsumer = new MyKafkaConsumer<>(dataProcessor);
    }

    @Override
    public void run() {
        kafkaConsumer.subscribe(List.of(topicToConsumeFrom));
        kafkaConsumer.startConsume();
    }
}
