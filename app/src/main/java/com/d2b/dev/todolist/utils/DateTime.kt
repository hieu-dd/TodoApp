package com.d2b.dev.todolist.utils

import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.*

object DateTime {
    @JvmStatic
    fun formatDate(time: Instant): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(Date(time.toEpochMilliseconds()))
    }
}