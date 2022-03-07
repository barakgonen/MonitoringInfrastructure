package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ConsumerProperties {
    @JsonProperty("bootstrap.servers")
    private String bootstrapServers;

    public ConsumerProperties(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public ConsumerProperties() {
    }
}
