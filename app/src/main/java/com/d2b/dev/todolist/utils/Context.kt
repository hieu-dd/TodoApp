package com.d2b.dev.todolist.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.datetime.Instant

fun Context.showDatePicker(date: Instant? = null, onSelected: (Instant) -> Unit) {
    val activity = this as? AppCompatActivity ?: return
    val dateBuilder = MaterialDatePicker.Builder.datePicker().apply {
        if (date != null) {
            setSelection(date.toEpochMilliseconds())
        }
    }
    val picker = dateBuilder.build()
    picker.show(activity.supportFragmentManager, "")
    picker.addOnPositiveButtonClickListener { millis ->
        onSelected(Instant.fromEpochMilliseconds(millis))
    }
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}