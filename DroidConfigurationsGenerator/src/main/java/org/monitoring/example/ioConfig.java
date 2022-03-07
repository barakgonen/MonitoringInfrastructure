package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ioConfig {
    private String topic;
    private InputFormat inputFormat;
    private ConsumerProperties consumerProperties;
    private int replicas;
    private int taskCount;
    private String taskDuration;
    private String type;
    private boolean useEarliestOffset;

    public ioConfig(String topic, InputFormat inputFormat, ConsumerProperties consumerProperties, int replicas, int taskCount, String taskDuration, String type, boolean useEarliestOffset) {
        this.topic = topic;
        this.inputFormat = inputFormat;
        this.consumerProperties = consumerProperties;
        this.replicas = replicas;
        this.taskCount = taskCount;
        this.taskDuration = taskDuration;
        this.type = type;
        this.useEarliestOffset = useEarliestOffset;
    }

    public ioConfig() {
    }
}