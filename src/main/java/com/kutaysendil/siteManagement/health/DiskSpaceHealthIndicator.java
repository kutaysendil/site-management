package com.kutaysendil.siteManagement.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class DiskSpaceHealthIndicator implements HealthIndicator {

    private static final long THRESHOLD_BYTES = 1024L * 1024L * 100L; // 100 MB

    @Override
    public Health health() {
        File root = new File("/");
        long freeSpace = root.getFreeSpace();

        if (freeSpace < THRESHOLD_BYTES) {
            return Health.down()
                    .withDetail("freeSpace", freeSpace)
                    .withDetail("threshold", THRESHOLD_BYTES)
                    .withDetail("unit", "bytes")
                    .build();
        }

        return Health.up()
                .withDetail("freeSpace", freeSpace)
                .withDetail("unit", "bytes")
                .build();
    }
}