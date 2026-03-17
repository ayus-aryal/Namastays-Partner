package com.example.namastays_partner.utilities

fun formatTo12Hour(hour: Int, minute: Int): String {
    val amPm = if (hour >= 12) "PM" else "AM"
    val formattedHour = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }
    val formattedMinute = minute.toString().padStart(2, '0')

    return "$formattedHour:$formattedMinute $amPm"
}