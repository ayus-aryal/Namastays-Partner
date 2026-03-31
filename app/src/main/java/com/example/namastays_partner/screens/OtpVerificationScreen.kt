package com.example.namastays_partner.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.namastays_partner.ui.theme.GradientButton
import com.example.namastays_partner.ui.theme.InterFont
import com.example.namastays_partner.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun OtpVerificationScreen(
    phone: String,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // ── Palette — identical to BasicInfoScreen ────────────────────────────────
    val primaryBlue = Color(0xFF1F3A68)
    val bgColor     = Color(0xFFF3F4F6)
    val cardColor   = Color.White

    val scrollState = rememberScrollState()

    var otp         by remember { mutableStateOf("") }
    var otpError    by remember { mutableStateOf<String?>(null) }
    var secondsLeft by remember { mutableStateOf(30) }
    var canResend   by remember { mutableStateOf(false) }
    var isVerifying by remember { mutableStateOf(false) }
    var isSuccess   by remember { mutableStateOf(false) }

    val verificationSuccess = authViewModel.verificationSuccess
    val verificationError   = authViewModel.verificationError   // expose String? in ViewModel

    // ── Countdown ─────────────────────────────────────────────────────────────
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

    // ── Navigate on success ───────────────────────────────────────────────────
    LaunchedEffect(verificationSuccess) {
        if (verificationSuccess) {
            isSuccess   = true
            isVerifying = false
            delay(500L)
            navController.navigate("location_details_screen") {
                popUpTo("otp_verification_screen") { inclusive = true }
            }
        }
    }

    // ── Handle API error ──────────────────────────────────────────────────────
    LaunchedEffect(verificationError) {
        if (!verificationError.isNullOrEmpty()) {
            isVerifying = false
            otpError    = verificationError
            otp         = ""
        }
    }

    // ── Root layout — mirrors BasicInfoScreen exactly ─────────────────────────
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .verticalScroll(scrollState)
            .imePadding()
            .padding(vertical = 20.dp)
    ) {

        // ── Top Bar ───────────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Registration",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                fontFamily = InterFont
            )
        }

        // ── Step Progress ─────────────────────────────────────────────────────
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "STEP 1 OF 7",
                    fontSize = 12.sp,
                    color = primaryBlue,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFont
                )
                Text(
                    "14% Completed",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = InterFont
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { 0.14f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(50)),
                color = primaryBlue,
                trackColor = Color(0xFFE5E7EB)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── Heading ───────────────────────────────────────────────────────────
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                "Phone Verification",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFont
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Enter the 6-digit code sent to +977 $phone to verify your account.",
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                lineHeight = 20.sp,
                fontFamily = InterFont,
                fontWeight = FontWeight.ExtraLight
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Card ──────────────────────────────────────────────────────────────
        Card(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                // Field label — same pattern as "Full Name *" in BasicInfoScreen
                Row {
                    Text(
                        "Verification Code ",
                        fontWeight = FontWeight.Light,
                        fontFamily = InterFont
                    )
                    Text(
                        "*",
                        fontWeight = FontWeight.Light,
                        fontFamily = InterFont,
                        color = Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                OtpInputField(
                    otp         = otp,
                    hasError    = otpError != null,
                    isSuccess   = isSuccess,
                    primaryBlue = primaryBlue,
                    onOtpChange = { newOtp ->
                        otpError = null
                        otp      = newOtp
                    }
                )

                // Error — same 12sp Red ExtraLight pattern as field errors
                otpError?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = InterFont,
                        fontWeight = FontWeight.ExtraLight
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ── Resend row ────────────────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (canResend)
                            "Didn't receive the code?"
                        else
                            "Resend code in ${secondsLeft}s",
                        fontSize = 13.sp,
                        color = Color(0xFF6B7280),
                        fontFamily = InterFont,
                        fontWeight = FontWeight.ExtraLight
                    )

                    if (canResend) {
                        Text(
                            text = "Resend OTP",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = primaryBlue,
                            fontFamily = InterFont,
                            modifier = Modifier.clickable {
                                authViewModel.sendOtp(phone)
                                otp      = ""
                                otpError = null
                                canResend = false
                            }
                        )
                    }
                }
            }
        }

        // ── Weight spacer — same pattern as BasicInfoScreen ───────────────────
        Spacer(modifier = Modifier.weight(1f))

        // ── Loading — same as BasicInfoScreen ─────────────────────────────────
        if (isVerifying) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 12.dp),
                color = primaryBlue
            )
        }

        // ── GradientButton — same component + padding as BasicInfoScreen ──────
        GradientButton(
            text = if (isSuccess) "Verified ✓" else "Verify OTP",
            modifier = Modifier.padding(horizontal = 20.dp),
            enabled = otp.length == 6 && !isVerifying && !isSuccess,
            onClick = {
                otpError = if (otp.length != 6)
                    "Please enter the complete 6-digit code"
                else null

                if (otpError == null) {
                    isVerifying = true
                    authViewModel.verifyOtp(navController.context,phone, otp)
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // ── API error — same pattern as BasicInfoScreen's errorMessage ─────────
        authViewModel.errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontFamily = InterFont,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}


// ─── OTP Input Field ──────────────────────────────────────────────────────────

@Composable
fun OtpInputField(
    otpLength: Int = 6,
    otp: String,
    hasError: Boolean,
    isSuccess: Boolean,
    primaryBlue: Color,
    onOtpChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(300L)
        focusRequester.requestFocus()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Invisible field — captures all keyboard input including paste
        BasicTextField(
            value = otp,
            onValueChange = { raw ->
                val filtered = raw.filter { it.isDigit() }.take(otpLength)
                onOtpChange(filtered)
            },
            modifier = Modifier
                .size(1.dp)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            cursorBrush = SolidColor(Color.Transparent)
        )

        // Visual boxes — tap to refocus
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.clickable { focusRequester.requestFocus() }
        ) {
            repeat(otpLength) { index ->
                OtpBox(
                    char        = otp.getOrNull(index)?.toString() ?: "",
                    isFocused   = index == otp.length && !hasError && !isSuccess,
                    hasError    = hasError,
                    isSuccess   = isSuccess,
                    isFilled    = index < otp.length,
                    primaryBlue = primaryBlue
                )
            }
        }
    }
}


// ─── Single OTP Box — border radius matches OutlinedTextField(shape = RoundedCornerShape(14.dp)) ──

@Composable
fun OtpBox(
    char: String,
    isFocused: Boolean,
    hasError: Boolean,
    isSuccess: Boolean,
    isFilled: Boolean,
    primaryBlue: Color
) {
    val borderColor by animateColorAsState(
        targetValue = when {
            hasError  -> Color.Red
            isSuccess -> Color(0xFF10B981)
            isFocused -> primaryBlue
            isFilled  -> primaryBlue.copy(alpha = 0.45f)
            else      -> Color(0xFFD1D5DB)
        },
        animationSpec = tween(180),
        label = "otp_border"
    )

    val bgColor by animateColorAsState(
        targetValue = when {
            hasError  -> Color.Red.copy(alpha = 0.05f)
            isSuccess -> Color(0xFF10B981).copy(alpha = 0.06f)
            isFilled  -> primaryBlue.copy(alpha = 0.04f)
            else      -> Color(0xFFF3F4F6)
        },
        animationSpec = tween(180),
        label = "otp_bg"
    )

    val scale by animateFloatAsState(
        targetValue = if (isFilled && !hasError) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness    = Spring.StiffnessMedium
        ),
        label = "otp_scale"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .size(width = 46.dp, height = 54.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .border(
                width = if (isFocused) 2.dp else 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (char.isNotEmpty()) {
            Text(
                text = char,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = InterFont,
                color = when {
                    hasError  -> Color.Red
                    isSuccess -> Color(0xFF10B981)
                    else      -> Color(0xFF111827)
                },
                textAlign = TextAlign.Center
            )
        } else if (isFocused) {
            BlinkingCursor(primaryBlue)
        }
    }
}


// ─── Blinking Cursor ──────────────────────────────────────────────────────────

@Composable
private fun BlinkingCursor(color: Color) {
    var visible by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(500L)
            visible = !visible
        }
    }
    Box(
        modifier = Modifier
            .width(2.dp)
            .height(22.dp)
            .clip(RoundedCornerShape(1.dp))
            .background(if (visible) color else Color.Transparent)
    )
}