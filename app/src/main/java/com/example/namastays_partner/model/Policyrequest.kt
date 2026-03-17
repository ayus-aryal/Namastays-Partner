package com.example.namastays_partner.model

data class PolicyRequest(
    val checkInTime: String = "",
    val checkOutTime: String = "",
    val extraGuestPrice: String = "",
    val smokingAllowed: Boolean = false,
    val childrenAllowed: Boolean = true,
    val petsAllowed: Boolean = false,
    val breakfastIncluded: Boolean = false,
    val cancellationPolicy: String = "Flexible (Full refund 1 day prior)"
)