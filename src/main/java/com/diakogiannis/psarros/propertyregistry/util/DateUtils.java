package com.diakogiannis.psarros.propertyregistry.util;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class DateUtils {
    public Long calculateDaysBetween(LocalDateTime dateTime) {

        return Duration.between(dateTime, LocalDateTime.now()).toDays();
    }
}
