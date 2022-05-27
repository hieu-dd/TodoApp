package com.d2b.dev.todolist.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.*

fun Instant.formatDate(pattern: String = "dd/MM/yyyy"): String {
    return SimpleDateFormat(pattern).format(Date(this.toEpochMilliseconds()))
}

fun Instant.isOutDate(): Boolean {
    val now = Clock.System.now()
    return when {
        getYear() < now.getYear() -> true
        getYear() == now.getYear() && getMonth() < now.getMonth() -> true
        getYear() == now.getYear() && getMonth() == now.getMonth() && getDay() < now.getDay() -> true
        else -> false
    }
}

fun Instant.getDay() = formatDate("dd").toInt()

fun Instant.getMonth() = formatDate("MM").toInt()

fun Instant.getYear() = formatDate("yyyy").toInt()