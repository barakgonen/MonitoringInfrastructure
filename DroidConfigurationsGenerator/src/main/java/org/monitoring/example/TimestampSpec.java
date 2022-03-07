package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TimestampSpec {
    private String column;
    private String format;

    public TimestampSpec(String column, String format) {
        this.column = column;
        this.format = format;
    }

    public TimestampSpec() {
    }
}
