package org.example.data.collector;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import java.util.function.Consumer;

public class DataProcessor<K, V> implements Consumer<ConsumerRecords<K, V>> {
    private final ElasticSearchWriter<K, V> elasticSearchWriter;

    public DataProcessor(ElasticSearchWriter<K, V> elasticSearchWriter) {
        this.elasticSearchWriter = elasticSearchWriter;
    }

    @Override
    public void accept(ConsumerRecords<K, V> kvConsumerRecord) {
        elasticSearchWriter.writeToIndex(kvConsumerRecord);
    }
}
