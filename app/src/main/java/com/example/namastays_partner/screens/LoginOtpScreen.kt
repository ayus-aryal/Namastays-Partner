package com.example.namastays_partner.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.namastays_partner.ui.theme.GradientButton
import com.example.namastays_partner.ui.theme.InterFont
import com.example.namastays_partner.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginOtpScreen(
    phone: String,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val primaryBlue = Color(0xFF1F3A68)

    var otp by remember { mutableStateOf("") }
    var otpError by remember { mutableStateOf<String?>(null) }
    var secondsLeft by remember { mutableStateOf(30) }
    var canResend by remember { mutableStateOf(false) }
    var isVerifying by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    val verificationSuccess = authViewModel.verificationSuccess
    val verificationError = authViewModel.verificationError

    // Countdown timer
    LaunchedEffect(canResend) {
        if (!canResend) {
            secondsLeft = 30
            while (secondsLeft > 0) {
                delay(1000L)
                secondsLeft--
            }
            canResend = true
        }
    }

    // Navigate on OTP verification success
    LaunchedEffect(verificationSuccess) {
        if (verificationSuccess) {
            isSuccess = true
            isVerifying = false

            authViewModel.fetchVendorStatusByPhone(phone) { status ->
                when (status) {
                    "VERIFIED" -> navController.navigate("home_screen") {
                        popUpTo("login_otp_screen") { inclusive = true }
                    }

                    "PENDING_VERIFICATION" -> navController.navigate("waiting_screen") {
                        popUpTo("login_otp_screen") { inclusive = true }
                    }

                    else -> navController.navigate("basic_info_screen") {
                        popUpTo("login_otp_screen") { inclusive = true }
                    }
                }
            }
        }
    }

    // Handle API error
    LaunchedEffect(verificationError) {
        if (!verificationError.isNullOrEmpty()) {
            isVerifying = false
            otpError = verificationError
            otp = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Login - OTP Verification", fontSize = 24.sp, fontFamily = InterFont)

        Spacer(modifier = Modifier.height(24.dp))

        OtpInputField(
            otp = otp,
            hasError = otpError != null,
            isSuccess = isSuccess,
            primaryBlue = primaryBlue,
            onOtpChange = { newOtp ->
                otpError = null
                otp = newOtp
            }
        )

        otpError?.let {
            Spacer(modifier = Modifier.height(6.dp))
            Text(it, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Resend OTP row
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (canResend) "Didn't receive code?" else "Resend in ${secondsLeft}s",
                fontSize = 14.sp
            )
            if (canResend) {
                Text(
                    text = "Resend OTP",
                    color = primaryBlue,
                    modifier = Modifier.clickable {
                        authViewModel.sendOtp(phone)
                        otp = ""
                        otpError = null
                        canResend = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        GradientButton(
            text = if (isSuccess) "Verified ✓" else "Verify OTP",
            enabled = otp.length == 6 && !isVerifying && !isSuccess,
            onClick = {
                otpError = if (otp.length != 6) "Enter complete 6-digit OTP" else null
                if (otpError == null) {
                    isVerifying = true
                    authViewModel.verifyOtp(navController.context, phone, otp)
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isVerifying) {
            CircularProgressIndicator(color = primaryBlue)
        }
    }
}