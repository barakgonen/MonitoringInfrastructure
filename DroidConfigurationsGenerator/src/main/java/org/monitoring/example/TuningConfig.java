package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TuningConfig {
    private String type;
    private int maxRowsPerSegment;

    public TuningConfig() {
    }

    public TuningConfig(String type, int maxRowsPerSegment) {
        this.type = type;
        this.maxRowsPerSegment = maxRowsPerSegment;
    }
}
