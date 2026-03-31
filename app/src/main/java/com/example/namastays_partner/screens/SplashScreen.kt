package com.example.namastays_partner.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.namastays_partner.R
import com.example.namastays_partner.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun SplashScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {

        // Step 1: fetch token
        val token =
            com.example.namastays_partner.utilities.TokenManager.getToken(context).firstOrNull()

        if (token.isNullOrEmpty()) {
            // No token -> go to welcome/registration
            navController.navigate("welcome") {
                popUpTo("splash_screen") { inclusive = true }
            }
        } else {
            // Token exists -> check backend
            authViewModel.fetchVendorStatus(context)

            // Wait until API check is done
            while (authViewModel.isLoading) {
                kotlinx.coroutines.delay(100)
            }

            when (authViewModel.vendorStatus) {
                "VERIFIED" -> navController.navigate("home_screen") {
                    popUpTo("splash_screen") { inclusive = true }
                }

                "PENDING_VERIFICATION" -> navController.navigate("waiting_screen") {
                    popUpTo("splash_screen") { inclusive = true }
                }

                else -> {
                    // If backend fails or returns NOT_REGISTERED -> clear token & go to registration
                    com.example.namastays_partner.utilities.TokenManager.clearToken(context)
                    navController.navigate("welcome") {
                        popUpTo("splash_screen") { inclusive = true }
                    }
                }
            }
        }
    }

    // Simple UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally){

            Image(
                painter = painterResource(id = R.drawable.namastays_partner_logo_upscaled_bg_removed),
                contentDescription = "App Logo",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit

            )
        }

    }
}