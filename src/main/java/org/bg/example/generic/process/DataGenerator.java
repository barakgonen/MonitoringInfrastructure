package org.bg.example.generic.process;

import org.bg.avro.structures.base.objects.Coordinate;
import org.bg.avro.structures.base.objects.CoordinateWithId;
import org.bg.avro.structures.base.objects.Metadata;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class DataGenerator {

    private static final double rangeMin = 0;
    private static final double rangeMax = 180.0;

    public static double getNextRandomDouble() {
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

    public static CoordinateWithId generateData() {
        long nowTimeMillis = Instant.now().toEpochMilli();
        return CoordinateWithId.newBuilder()
                .setId(Metadata.newBuilder()
                        .setContextId(UUID.randomUUID().toString())
                        .setStartTimeMillis(nowTimeMillis)
                        .setSendTimeMillis(nowTimeMillis)
                        .setDiffSinceSendMillis(nowTimeMillis - nowTimeMillis)
                        .build())
                .setPosition(Coordinate.newBuilder()
                        .setAltitude(getNextRandomDouble())
                        .setLatitude(getNextRandomDouble())
                        .setLongitude(getNextRandomDouble())
                        .build())
                .build();
    }
}
