package com.course.kafka.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Section 16: 97. Timestamp
 *
 */
public class LocalDateTimeUtil {

	public static long toEpochTimestamp(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

}
