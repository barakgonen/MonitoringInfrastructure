package org.example.data.collector;

public class Constants {
    public static final String KAFKA_ADDRESS = System.getenv("KAFKA_ADDRESS");
    public static final String SCHEMA_REGISTRY_URL = System.getenv("SCHEMA_REGISTRY_URL");
    public static final String ELASTIC_HOST = System.getenv("ELASTIC_HOST");
    public static final String ELASTIC_USER_NAME = System.getenv("ELASTIC_USER_NAME");
    public static final String ELASTIC_PASSWORD = System.getenv("ELASTIC_PASSWORD");
}
