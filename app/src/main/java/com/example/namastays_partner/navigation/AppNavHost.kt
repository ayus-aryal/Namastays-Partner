package com.example.namastays_partner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.namastays_partner.screens.AmenitiesScreen
import com.example.namastays_partner.screens.BasicInfoScreen
import com.example.namastays_partner.screens.HomeScreen
import com.example.namastays_partner.screens.LocationDetailsScreen
import com.example.namastays_partner.screens.LoginScreen
import com.example.namastays_partner.screens.MediaUploadScreen
import com.example.namastays_partner.screens.OtpVerificationScreen
import com.example.namastays_partner.screens.PricingAndPoliciesScreen
import com.example.namastays_partner.screens.PropertyInfoScreen
import com.example.namastays_partner.screens.ReviewScreen
import com.example.namastays_partner.screens.RoomDetailsScreen
import com.example.namastays_partner.screens.SplashScreen
import com.example.namastays_partner.screens.WaitingScreen
import com.example.namastays_partner.screens.WelcomeScreen
import com.example.namastays_partner.viewmodel.AuthViewModel
import com.example.namastays_partner.viewmodel.VendorOnboardingViewModel

@Composable
fun AppNavHost(navController: NavHostController){


    val vendorViewModel: VendorOnboardingViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current



    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ){

        // ---- SPLASH SCREEN ----
        composable("splash_screen") {
            SplashScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // ---- WAITING SCREEN ----
        composable("waiting_screen") {
            WaitingScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("welcome"){
            WelcomeScreen(navController)
        }

        composable("basic_info_screen"){
            BasicInfoScreen(navController, vendorViewModel, authViewModel)
        }

        composable("otp_screen/{phone}") { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("phone") ?: ""

            OtpVerificationScreen(
                phone = "+977$phone",
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("location_details_screen"){
            LocationDetailsScreen(navController, vendorViewModel)
        }

        composable("property_info_screen"){
            PropertyInfoScreen(navController, vendorViewModel)
        }

        composable("room_details_screen"){
            RoomDetailsScreen(navController, vendorViewModel)
        }

        composable("amenities_screen"){
            AmenitiesScreen(navController, vendorViewModel)
        }

        composable("media_upload_screen"){
            MediaUploadScreen(navController, vendorViewModel)
        }

        composable("pricing_and_policies_screen"){
            PricingAndPoliciesScreen(navController, vendorViewModel)
        }

        composable("review_screen"){
            ReviewScreen(navController, vendorViewModel)
        }

        composable("home_screen"){
            HomeScreen()
        }

        composable("login_screen"){
            LoginScreen(navController)
        }

    }
}