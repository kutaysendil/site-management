package com.kutaysendil.siteManagement.utils;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final String TURKEY_ZONE = "Europe/Istanbul";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime nowTurkeyLocal() {
        return ZonedDateTime.now(ZoneId.of(TURKEY_ZONE)).toLocalDateTime();
    }

    public static String nowTurkeyAsString() {
        return nowTurkeyLocal().format(formatter);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public static LocalDateTime convertToTurkeyTime(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of(TURKEY_ZONE))
                .toLocalDateTime();
    }

    public static boolean isExpired(LocalDateTime expirationTime) {
        return nowTurkeyLocal().isAfter(expirationTime);
    }

    public static LocalDateTime addDays(LocalDateTime dateTime, long days) {
        return dateTime.plusDays(days);
    }

    public static LocalDateTime addHours(LocalDateTime dateTime, long hours) {
        return dateTime.plusHours(hours);
    }
}