package org.example.data.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;

import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public class ElasticSearchWriter<K, V> {
    private Collection<String> topics;
    private RestClientBuilder elasticsearchClient;
    private RestHighLevelClient hlrc;

    public ElasticSearchWriter(Collection<String> topics) {
        this.topics = topics;
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "Aa123456"));

        this.elasticsearchClient = RestClient.builder(
                        new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(
                            HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        this.hlrc = new RestHighLevelClient(elasticsearchClient);
    }

    public void createIndexes() {
        this.topics.forEach(s -> createIndex("from_" + s.toLowerCase(Locale.ROOT)));
    }

    private void createIndex(String indexName) {

        try {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
//            HashMap<String, String> mapping = new HashMap<>();
//            createIndexRequest.mapping(mapping);
            hlrc.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToIndex(ConsumerRecords<K, V> kvConsumerRecords) {
        ObjectMapper mapper = new ObjectMapper();
        BulkRequest request = new BulkRequest();
        try {
            for (ConsumerRecord<K, V> kvConsumerRecord : kvConsumerRecords) {
                Map<String, String> map = mapper.readValue(kvConsumerRecord.value().toString(), Map.class);

                request.add(
                        new IndexRequest(kvConsumerRecord.topic().toLowerCase(Locale.ROOT))
                                .id(String.valueOf(kvConsumerRecord.offset()))
                                .source(map));


            }

            BulkResponse response = hlrc.bulk(request, RequestOptions.DEFAULT);
            System.out.println("took: " + response.getTook() + ", request.size: " + request.requests().size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
