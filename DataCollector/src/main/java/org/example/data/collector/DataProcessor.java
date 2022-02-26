package org.example.data.collector;

import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.function.Consumer;

public class DataProcessor<K, V> implements Consumer<ConsumerRecords<K, V>> {
    private ElasticSearchWriter elasticSearchWriter;

    public DataProcessor(ElasticSearchWriter elasticSearchWriter) {
        this.elasticSearchWriter = elasticSearchWriter;
    }

    @Override
    public void accept(ConsumerRecords<K, V> kvConsumerRecord) {
        elasticSearchWriter.writeToIndex(kvConsumerRecord);
    }
}
