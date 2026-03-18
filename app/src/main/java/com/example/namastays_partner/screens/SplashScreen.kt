package com.example.namastays_partner.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.namastays_partner.viewmodel.AuthViewModel

@Composable
fun SplashScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        authViewModel.fetchVendorStatus(context)

        // Wait until API check is done
        while (authViewModel.isLoading) {
            kotlinx.coroutines.delay(100)
        }

        // Navigate based on backend status
        when (authViewModel.vendorStatus) {
            "VERIFIED" -> navController.navigate("home_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
            "PENDING_VERIFICATION", "NOT_REGISTERED" -> navController.navigate("waiting_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
            else -> navController.navigate("welcome") {
                popUpTo("splash_screen") { inclusive = true }
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
        CircularProgressIndicator()
        Text(text = "Checking account...", modifier = Modifier.padding(top = 16.dp))
    }
}