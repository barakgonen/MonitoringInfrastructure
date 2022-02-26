package com.example.monitoring.rest;

import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.ml.job.results.Result;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

@RestController
public class QueriesController {
    @Autowired
    RestClientConfig restClientConfig;

    @GetMapping("/flow/{uuid}")
    public Collection<String> list(@PathVariable String uuid) {
        try {
            List<String> indicies = Arrays.stream(restClientConfig.elasticsearchClient().indices()
                    .get(new GetIndexRequest().indices("*"), RequestOptions.DEFAULT)
                    .getIndices()).map(s -> s).collect(Collectors.toList())
                    .stream().filter(s -> !s.startsWith(".")).collect(Collectors.toList());

            BoolQueryBuilder keywordBuilder = boolQuery();
            keywordBuilder.should(matchPhraseQuery("id.contextId", uuid));

            BoolQueryBuilder queryBuilder = boolQuery()
                    .must(keywordBuilder);
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(indicies.stream().toArray(String[]::new));


            return indicies;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
