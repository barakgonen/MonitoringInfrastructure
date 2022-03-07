package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FlattenSpec {
    private ArrayList<Field> fields;

    public FlattenSpec() {
        this.fields = new ArrayList<>();
    }

    public FlattenSpec(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field f) {
        this.fields.add(f);
    }
}
