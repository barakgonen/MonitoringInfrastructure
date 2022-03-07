package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Field {
    private String name;
    private String type;
    private String expr;

    public Field(String name, String type, String expr) {
        this.name = name;
        this.type = type;
        this.expr = expr;
    }

    public Field() {
    }
}
