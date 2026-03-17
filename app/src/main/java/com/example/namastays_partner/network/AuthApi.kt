package com.example.namastays_partner.network

import com.example.namastays_partner.model.AuthApiResponse
import com.example.namastays_partner.model.SendOtpRequest
import com.example.namastays_partner.model.VerifyOtpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/send-otp")
    suspend fun sendOtp(
        @Body request: SendOtpRequest
    ): Response<AuthApiResponse>

    @POST("/auth/verify-otp")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequest
    ): Response<AuthApiResponse>
}