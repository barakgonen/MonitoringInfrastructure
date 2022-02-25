/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.example.data.collector;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        MyKafkaConsumer consumer = new MyKafkaConsumer();
        HashSet<String> topics = (HashSet<String>) consumer.listTopics().entrySet().stream().filter(o -> ((Map.Entry<String, List<PartitionInfo>>) o).getKey().contains("-")).map(o -> ((Map.Entry<String, List<PartitionInfo>>) o).getKey()).collect(Collectors.toSet());
        System.out.println(topics);
        consumer.close();
        ElasticSearchWriter elasticSearchWriter = new ElasticSearchWriter(topics);
        elasticSearchWriter.createIndexes();

        ExecutorService executorService = Executors.newFixedThreadPool(topics.size());
        for (String topic : topics) {
            DataProcessor dataProcessor = new DataProcessor(elasticSearchWriter);
            final MyKafkaConsumer specificTopicConsumer = new MyKafkaConsumer<>(dataProcessor);
            specificTopicConsumer.subscribe(List.of(topic));
            executorService.submit(() -> specificTopicConsumer.startConsume());
        }
        System.out.println("BGBG");
    }
}
