package com.example.monitoring.rest;

import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class QueriesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueriesController.class);
    @Autowired
    RestClientConfig restClientConfig;

    @GetMapping("/flow/{uuid}")
    public SystemFlow getMessageFlowByUuid(@PathVariable String uuid) {
        List<String> indices = listAllPossibleIndices();
        if (!indices.isEmpty()) {
            SearchRequest searchRequest = new SearchRequest(indices.stream().toArray(String[]::new));
            searchRequest.source(getSearchSourceBuilder(uuid));
            SearchResponse response = executeSearch(searchRequest);
            if (response != null) {
                return parseSearchResponse(response);
            } else {
                LOGGER.error("returning null to client for request because search response is null");
                return null;
            }
        } else {
            LOGGER.error("returning null to client for request because indices are empty");
            return null;
        }
    }

    private SystemFlow parseSearchResponse(SearchResponse searchResponse) {
        List<SearchHit> searchHits = Arrays.stream(searchResponse.getHits().getHits()).collect(Collectors.toList());
        Queue<Process> wholeFlow = new LinkedList<>();
        searchHits.forEach(documentFields -> wholeFlow.add(new Process(documentFields)));
        return new SystemFlow(wholeFlow);
    }

    private SearchResponse executeSearch(SearchRequest searchRequest) {
        try {
            SearchResponse response = restClientConfig.elasticsearchClient()
                    .search(searchRequest, RequestOptions.DEFAULT);
            LOGGER.debug("search took: {}", response.getTook());
            return response;
        } catch (IOException e) {
            LOGGER.error("caught an exception during search: {}", e.getMessage());
        }
        return null;
    }

    private SearchSourceBuilder getSearchSourceBuilder(String uuid) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder qb = QueryBuilders.matchPhraseQuery("id.contextId", uuid);
        searchSourceBuilder.sort("id.sendTimeMillis", SortOrder.ASC);
        searchSourceBuilder.query(qb);
        return searchSourceBuilder;
    }

    private List<String> listAllPossibleIndices() {
        try {
            return Arrays.stream(restClientConfig.elasticsearchClient().indices()
                            .get(new GetIndexRequest().indices("*"), RequestOptions.DEFAULT)
                            .getIndices()).collect(Collectors.toList())
                    .stream().filter(s -> !s.startsWith(".")).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("failed to get all indices... error is: {}", e.getMessage());
        }

        return Collections.emptyList();
    }
}
