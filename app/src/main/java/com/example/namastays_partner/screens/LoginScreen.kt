package com.example.namastays_partner.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import com.example.namastays_partner.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import com.example.namastays_partner.utilities.PhoneUtils

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    var phone by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val isLoading = authViewModel.isLoading
    val otpSent = authViewModel.otpSent

    val normalizedPhone = PhoneUtils.normalizeNepalPhone(phone)


    // 🔥 Navigate ONLY when OTP is successfully sent
    LaunchedEffect(authViewModel.otpSent) {
        if (authViewModel.otpSent) {
            authViewModel.resetOtpState()
            navController.navigate("login_otp_screen/$normalizedPhone")
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Login",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it.filter { ch -> ch.isDigit() }.take(10)
                error = null
            },
            label = { Text("Phone Number") },
            leadingIcon = { Text("+977 ") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        error?.let {
            Spacer(modifier = Modifier.height(6.dp))
            Text(it, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (phone.length != 10) {
                    error = "Enter valid phone number"
                } else {

                    authViewModel.checkUserExists(phone) { exists ->

                        if (exists) {
                            authViewModel.resetOtpState()
                            authViewModel.sendOtp(normalizedPhone)
                        } else {
                            error = "User not registered. Please sign up."
                        }
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.height(18.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Send OTP")
            }
        }
    }
}