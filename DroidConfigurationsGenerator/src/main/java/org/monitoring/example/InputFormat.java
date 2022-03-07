package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class InputFormat {
    private String type;
    private AvroBytesDecoder avroBytesDecoder;
    private FlattenSpec flattenSpec;

    public InputFormat(String type, AvroBytesDecoder avroBytesDecoder, FlattenSpec flattenSpec) {
        this.type = type;
        this.avroBytesDecoder = avroBytesDecoder;
        this.flattenSpec = flattenSpec;
    }

    public InputFormat() {
    }
}
