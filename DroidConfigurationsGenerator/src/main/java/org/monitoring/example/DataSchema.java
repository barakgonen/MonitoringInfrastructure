package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DataSchema {
    private String dataSource;
    private TimestampSpec timestampSpec;
    private DimensionsSpec dimensionsSpec;
    private GranularitySpec granularitySpec;

    public DataSchema(String dataSource, TimestampSpec timestampSpec, DimensionsSpec dimensionsSpec, GranularitySpec granularitySpec) {
        this.dataSource = dataSource;
        this.timestampSpec = timestampSpec;
        this.dimensionsSpec = dimensionsSpec;
        this.granularitySpec = granularitySpec;
    }

    public DataSchema() {
    }
}
