package com.example.monitoring.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.elasticsearch.search.SearchHit;

import java.util.HashMap;
import java.util.Locale;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Process {
    String reporterProcess;
    String topicName;
    Object producedMessage;

    public Process() {
    }

    public Process(SearchHit searchHit) {
        this.topicName = indexToTopic(searchHit.getIndex());
        this.producedMessage = searchHit.getSourceAsMap();
        this.reporterProcess = (String)((HashMap) this.producedMessage).get("reporterProcess");
        ((HashMap<?, ?>) this.producedMessage).remove("reporterProcess");
    }

    private String indexToTopic(String index) {
        return index.substring(index.lastIndexOf('_') + 1).toUpperCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return "Process{" +
                "serviceName='" + reporterProcess + '\'' +
                ", topicName='" + topicName + '\'' +
                ", producedMessage=" + producedMessage +
                '}' + "\n";
    }
}
