package org.monitoring.example;

public class VersionedSchema {
    private int id;
    private String subject;

    public VersionedSchema(int id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public String getTopicName() {
        // Assuming key of schema registry's schema is TOPIC-NAME-value
        return this.subject.split("-value")[0];
    }
}
