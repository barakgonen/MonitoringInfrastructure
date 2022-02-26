package com.example.monitoring.rest;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import static com.example.monitoring.rest.Constants.*;

@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(ELASTIC_HOST)
                .withBasicAuth(ELASTIC_USER_NAME, ELASTIC_PASSWORD)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
