package com.javathlon.section19;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class DateOperations {

    public static void main(String[] args) {

        //i.e: 2020-01-08T23:45:21.392085300Z --> Z represent the offset and T separate the time.
        Instant nowInstant = Instant.now();
        System.out.println("now: " + nowInstant);

        Instant nowPlusFiveHours = nowInstant.plus(Duration.ofHours(5));
        System.out.println("now plus 5 hours: " + nowPlusFiveHours);

        Instant nowPlusTwoDays = nowInstant.plus(Period.ofDays(2));
        System.out.println("now plus 2 days:" + nowPlusTwoDays);

        Duration myDuration = Duration.between(nowPlusFiveHours, nowPlusTwoDays);
        System.out.println(myDuration);

        long timeDifferenceInSeconds = myDuration.getSeconds();
        System.out.println(timeDifferenceInSeconds);

        long days = TimeUnit.SECONDS.toDays(timeDifferenceInSeconds);
        long hours = TimeUnit.SECONDS.toHours(myDuration.minus(Duration.ofDays(days)).getSeconds());
        System.out.println(days + " days " + hours + " hours");

        Period period = Period.ofMonths(5);
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime timeFiveMinutesBefore = nowTime.minus(period);
        System.out.println("timeFiveMinutesBefore " + timeFiveMinutesBefore);







    }


}
