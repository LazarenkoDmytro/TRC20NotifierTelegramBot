package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class for date and time operations.
 * Provides functionality to format timestamps into a standardized date-time string.
 */
public class DateUtils {
    // Date format used for converting timestamps to strings. UTC timezone is set to ensure consistency.
    private static final SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Formats a given timestamp (in milliseconds) into a date-time string.
     * The format used is "yyyy-MM-dd HH:mm:ss" in UTC timezone.
     *
     * @param timestamp The timestamp to format, in milliseconds since the epoch.
     * @return A formatted date-time string representing the timestamp.
     */
    public static String formatTimestamp(long timestamp) {
        return dateFormat.format(new Date(timestamp));
    }
}
