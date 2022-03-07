package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AvroBytesDecoder {
    private String type;
    private String url;

    public AvroBytesDecoder(String type, String url) {
        this.type = type;
        this.url = url;
    }

    public AvroBytesDecoder() {
    }
}
