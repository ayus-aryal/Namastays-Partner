package com.example.namastays_partner.model

data class AddressRequest(
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val postalCode: String = "",
    val country: String = ""
)