package com.example.namastays_partner.utilities

object PhoneUtils {
    fun normalizeNepalPhone(phone: String): String {
        val cleaned = phone.trim().replace(" ", "")
        return if (cleaned.startsWith("+977")) cleaned else "+977$cleaned"
    }
}