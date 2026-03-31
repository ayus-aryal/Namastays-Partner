package com.example.namastays_partner.model

data class AuthApiResponse(
    val success: Boolean,
    val message: String,
    val token: String?
)
