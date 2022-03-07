package org.monitoring.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DimensionsSpec {
    private ArrayList<String> dimensionExclusions;
    private ArrayList<String> spatialDimensions;

    public DimensionsSpec() {
        this.dimensionExclusions = new ArrayList<>();
        this.spatialDimensions = new ArrayList<>();
    }
}
