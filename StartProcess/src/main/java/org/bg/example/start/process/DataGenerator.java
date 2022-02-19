package org.bg.example.start.process;

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
        long timeStamp = Instant.now().toEpochMilli();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CoordinateWithId.newBuilder()
                .setId(Metadata.newBuilder()
                        .setContextId(UUID.randomUUID().toString())
                        .setStartTimeMillis(timeStamp)
                        .build())
                .setPosition(Coordinate.newBuilder()
                        .setAltitude(getNextRandomDouble())
                        .setLatitude(getNextRandomDouble())
                        .setLongitude(getNextRandomDouble())
                        .build())
                .build();
    }
}
