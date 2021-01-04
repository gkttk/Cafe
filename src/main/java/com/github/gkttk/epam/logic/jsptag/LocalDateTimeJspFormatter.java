package com.github.gkttk.epam.logic.jsptag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeJspFormatter {
    private final static String DEFAULT_PATTERN = "dd.MM.yyyy HH.mm";
    private LocalDateTimeJspFormatter() {}

    public static String formatLocalDateTime(LocalDateTime localDateTime){
        return formatLocalDateTime(localDateTime, DEFAULT_PATTERN);
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
