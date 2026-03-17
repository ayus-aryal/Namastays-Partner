package com.example.namastays_partner.model

data class SendOtpRequest(
    val phone: String
)

data class VerifyOtpRequest(
    val phone: String,
    val otp: String
)