package com.example.namastays_partner.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.Paragraph
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.namastays_partner.model.SendOtpRequest
import com.example.namastays_partner.model.VerifyOtpRequest
import com.example.namastays_partner.network.RetrofitClient
import com.example.namastays_partner.network.RetrofitClientOtp
import com.example.namastays_partner.network.VendorApi
import com.example.namastays_partner.utilities.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var otpSent by mutableStateOf(false)
        private set

    var verificationSuccess by mutableStateOf(false)
        private set

    var verificationError by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var vendorStatus by mutableStateOf<String?>(null)
        private set

    var tokenExists by mutableStateOf(false)
        private set


    private val vendorApi = RetrofitClient.api

    fun sendOtp(phone: String) {

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {

                val response = RetrofitClientOtp.api.sendOtp(
                    SendOtpRequest(phone)
                )

                if (response.isSuccessful) {
                    otpSent = true
                } else {
                    errorMessage = "Failed to send OTP"
                }
            } catch (e: Exception) {
                errorMessage = e.message
            }
            isLoading = false
        }
    }

    fun verifyOtp(phone: String, otp: String) {

        println("VERIFY API CALLED")

        viewModelScope.launch {

            isLoading = true
            verificationError = null
            verificationSuccess = false

            try {

                val response = RetrofitClientOtp.api.verifyOtp(
                    VerifyOtpRequest(phone, otp)
                )

                if (response.isSuccessful) {

                    val body = response.body()

                    if (body?.success == true) {
                        verificationSuccess = true
                    } else {
                        verificationError = body?.message ?: "Invalid OTP"
                    }

                } else {

                    verificationError = "Invalid or expired OTP"

                }

            } catch (e: Exception) {

                verificationError = e.message ?: "Something went wrong"

            }

            isLoading = false
        }
    }


    fun resetOtpState() {
        otpSent = false
    }


    fun fetchVendorStatus(context: Context) {
        viewModelScope.launch {

            try {
                isLoading = true

                // 1️⃣ Get the locally stored token
                val token = TokenManager.getToken(context).first()
                tokenExists = !token.isNullOrEmpty()


                if (token.isNullOrEmpty()) {
                    vendorStatus = "NOT_REGISTERED"
                    Log.d("AUTH_DEBUG", "No token -> NOT_REGISTERED")
                    return@launch
                }

                // 2️⃣ Call backend
                val response = vendorApi.getVendorStatus("Bearer $token")

                if (response.isSuccessful) {
                    vendorStatus = response.body()?.status ?: "NOT_REGISTERED"
                    Log.d("AUTH_DEBUG", "Vendor Status: $vendorStatus")

                    // Optional: store status locally for faster navigation
                    // Example: TokenManager.saveVendorStatus(context, vendorStatus)

                } else {
                    Log.e("AUTH_ERROR", response.errorBody()?.string() ?: "Unknown error")
                    vendorStatus = "NOT_REGISTERED"
                }

            } catch (e: Exception) {
                e.printStackTrace()
                vendorStatus = "NOT_REGISTERED"
            } finally {
                isLoading = false
            }
        }
    }
}