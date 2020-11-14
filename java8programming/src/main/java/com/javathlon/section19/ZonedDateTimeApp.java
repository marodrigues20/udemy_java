package com.javathlon.section19;

import java.time.*;

public class ZonedDateTimeApp {

    public static void main(String[] args) {

        //i.e: 2020-01-09T10:01:25.571777400Z[Europe/London]
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);

        // I specified which time zone I want to know.
        //i.e: 2020-01-09T11:01:25.571777400+01:00[Europe/Berlin]
        ZoneId berlinZoneId = ZoneId.of("Europe/Berlin");
        ZonedDateTime berlinTime = zonedDateTime.withZoneSameInstant(berlinZoneId);
        System.out.println(berlinTime);

        //OffsetDateTime just show the offSet information and it doesn't show the zone information
        //i.e: [Europe/Berlin]
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        System.out.println(offsetDateTime);

        //If you don't know the exact offset but you know the tipical location you may use zone date time,
        // and zoneId in combination.
        //If you know the exact offset you may directly use OffsetDateTime.
        OffsetDateTime offsetUtc = offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC);
        System.out.println(offsetUtc);

        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        System.out.println(localDateTime);

        System.out.println(offsetDateTime.isAfter(offsetUtc));
        System.out.println(offsetDateTime.isBefore(offsetUtc));

        boolean isTimeSame = offsetDateTime.isEqual(offsetUtc);
        System.out.println(isTimeSame);

        ZoneOffset myZoneOffSet = ZoneOffset.ofHoursMinutes(3,30);
        System.out.println(myZoneOffSet);
        System.out.println(offsetDateTime.withOffsetSameInstant(myZoneOffSet));

        //Exactly the same above
        ZoneOffset myZoneOffsetString = ZoneOffset.of("+03:30");
        System.out.println(offsetDateTime.withOffsetSameInstant(myZoneOffsetString));



    }
}
