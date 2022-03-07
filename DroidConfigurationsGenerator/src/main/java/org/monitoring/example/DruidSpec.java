package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DruidSpec {
    private String type;
    private DruidRequestSpec spec;

    public DruidSpec(String type, DruidRequestSpec druidRequestSpec) {
        this.type = type;
        this.spec = druidRequestSpec;
    }

    public DruidSpec() {
    }
}
