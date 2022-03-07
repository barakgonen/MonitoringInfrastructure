package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GranularitySpec {
    private String queryGranularity;
    private boolean rollup;
    private String segmentGranularity;

    public GranularitySpec(String queryGranularity, boolean rollup, String segmentGranularity) {
        this.queryGranularity = queryGranularity;
        this.rollup = rollup;
        this.segmentGranularity = segmentGranularity;
    }

    public GranularitySpec() {
    }
}
