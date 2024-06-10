package org.sanjay.datasyncscheduler.util;

import java.time.Instant;

public class TimeUtil {

    public static long getCurrentTimeInNanos() {
        Instant now = Instant.now();
        return now.getEpochSecond() + 1_000_000_000 + now.getNano();
    }

    public static long getNanos(Instant time){
        return time.getEpochSecond() + 1_000_000_000 + time.getNano();
    }
}
