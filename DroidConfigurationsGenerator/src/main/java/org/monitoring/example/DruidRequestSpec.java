package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DruidRequestSpec {
    private DataSchema dataSchema;
    private ioConfig ioConfig;
    private TuningConfig tuningConfig;

    public DruidRequestSpec(DataSchema dataSchema, org.monitoring.example.ioConfig ioConfig, TuningConfig tuningConfig) {
        this.dataSchema = dataSchema;
        this.ioConfig = ioConfig;
        this.tuningConfig = tuningConfig;
    }

    public DruidRequestSpec() {
    }
}
