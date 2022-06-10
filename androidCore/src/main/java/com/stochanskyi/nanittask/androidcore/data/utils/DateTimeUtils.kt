package com.stochanskyi.nanittask.androidcore.data.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun utcLocalDate(millis: Long): LocalDate {
    return Instant.ofEpochMilli(millis)
        .atZone(utcZoneId())
        .toLocalDate()
}

fun utcZoneId() = ZoneId.of("UTC")