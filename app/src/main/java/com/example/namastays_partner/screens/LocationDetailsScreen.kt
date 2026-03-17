package com.example.namastays_partner.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.namastays_partner.ui.theme.GradientButton
import com.example.namastays_partner.ui.theme.InterFont
import com.example.namastays_partner.ui.theme.rememberAdaptiveSpacing
import com.example.namastays_partner.viewmodel.VendorOnboardingViewModel
import com.google.android.gms.location.LocationServices
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun LocationDetailsScreen(navController: NavController,
                          viewModel: VendorOnboardingViewModel) {

    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val spacing = rememberAdaptiveSpacing()
    val verticalEdgePadding = spacing.edge


    var addressError by remember { mutableStateOf<String?>(null) }
    var cityError by remember { mutableStateOf<String?>(null) }
    var stateError by remember { mutableStateOf<String?>(null) }
    var postalCodeError by remember { mutableStateOf<String?>(null) }
    var countryError by remember { mutableStateOf<String?>(null) }
    val primaryBlue = Color(0xFF1F3A68)
    val bgColor = Color(0xFFF3F4F6)
    val cardColor = Color.White

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    getAddressFromLocation(context, it) { addr ->
                        viewModel.updateAddress(addr.addressLine)
                        viewModel.updateCity(addr.city)
                        viewModel.updateState(addr.state)
                        viewModel.updatePostalCode(addr.postalCode)
                        viewModel.updateCountry(addr.country)
                    }
                }
            }
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .verticalScroll(scrollState)
            .imePadding()
            .padding(vertical = verticalEdgePadding)
    ) {

        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier
                .size(24.dp)
                .clickable {
                    navController.popBackStack()
                })
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Registration",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                fontFamily = InterFont
            )
        }

        // Step Progress
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "STEP 2 OF 7",
                    fontSize = 12.sp,
                    color = primaryBlue,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFont
                )
                Text(
                    "28% Completed",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = InterFont
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = 0.28f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(50)),
                color = primaryBlue,
                trackColor = Color(0xFFE5E7EB)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                "Location Details",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFont
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Provide accurate location details so travelers can easily find your property.",
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                lineHeight = 20.sp,
                fontFamily = InterFont,
                fontWeight = FontWeight.ExtraLight
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {

            Column(modifier = Modifier.padding(20.dp)) {

                //  Use My Current Location Button
                Button(
                    onClick = {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.MyLocation, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Use My Current Location", fontFamily = InterFont)
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    "OR",
                    fontSize = 15.sp,
                    fontFamily = InterFont,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(15.dp))

                // ADDRESS FIELDS
                OutlinedTextField(
                    value = viewModel.state.address.address,
                    onValueChange = {
                        viewModel.updateAddress(it)
                        addressError = null
                    },
                    isError = addressError != null,
                    placeholder = { Text("Full Address", fontFamily = InterFont, fontWeight = FontWeight.Light) },
                    leadingIcon = { Icon(Icons.Default.LocationOn, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                addressError?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(it, color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = viewModel.state.address.city,
                    onValueChange = {
                        viewModel.updateCity(it)
                        cityError = null
                    },
                    isError = cityError != null,
                    placeholder = { Text("City", fontFamily = InterFont, fontWeight = FontWeight.Light) },
                    leadingIcon = { Icon(Icons.Default.LocationCity, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                cityError?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = viewModel.state.address.state,
                    onValueChange = {
                        viewModel.updateState(it)
                        stateError = null
                    },
                    isError = stateError != null,
                    placeholder = { Text("State", fontFamily = InterFont, fontWeight = FontWeight.Light) },
                    leadingIcon = { Icon(Icons.Default.Map, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                stateError?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = viewModel.state.address.postalCode,
                    onValueChange = {
                        viewModel.updatePostalCode(it)
                        postalCodeError = null
                    },
                    isError = postalCodeError != null,
                    placeholder = { Text("Postal Code", fontFamily = InterFont, fontWeight = FontWeight.Light) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = { Icon(Icons.Default.MarkunreadMailbox, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                postalCodeError?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = viewModel.state.address.country,
                    onValueChange = {
                        viewModel.updateCountry(it)
                        countryError = null
                    },
                    isError = countryError != null,
                    placeholder = { Text("Country", fontFamily = InterFont, fontWeight = FontWeight.Light) },
                    leadingIcon = { Icon(Icons.Default.Public, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                countryError?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))




            }
        }
        // HERE

        Spacer(Modifier.height(20.dp))

        // Push Continue Button to bottom
        Spacer(modifier = Modifier.weight(1f))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            // Continue Button (same style)
            GradientButton(
                text = "Continue",
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    addressError =
                        if (viewModel.state.address.address.isBlank())
                            "Address is required"
                        else null

                    cityError =
                        if (viewModel.state.address.city.isBlank())
                            "City is required"
                        else null

                    stateError =
                        if (viewModel.state.address.state.isBlank())
                            "State is required"
                        else null

                    postalCodeError =
                        if (viewModel.state.address.postalCode.length < 4)
                            "Invalid postal code"
                        else null

                    countryError =
                        if (viewModel.state.address.country.isBlank())
                            "Country is required"
                        else null

                    if (addressError == null &&
                        cityError == null &&
                        stateError == null &&
                        postalCodeError == null &&
                        countryError == null
                    ) {
                        navController.navigate("property_info_screen")
                    }
                }
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}


// ---------------------- Address Helper ----------------------
data class AddressResult(
    val addressLine: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
)

fun getAddressFromLocation(
    context: Context,
    location: Location,
    onResult: (AddressResult) -> Unit
) {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

    if (!addresses.isNullOrEmpty()) {
        val address = addresses[0]
        onResult(
            AddressResult(
                addressLine = address.getAddressLine(0) ?: "",
                city = address.locality ?: "",
                state = address.adminArea ?: "",
                postalCode = address.postalCode ?: "",
                country = address.countryName ?: ""
            )
        )
    }
}

//@Composable
//@Preview(showBackground = true, device = Devices.NEXUS_5X)
//fun LocationDetailsScreenPreview() {
//    LocationDetailsScreen(navController = rememberNavController(),
//        viewModel = VendorOnboardingViewModel())
//}