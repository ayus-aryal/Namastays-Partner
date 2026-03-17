package com.example.namastays_partner.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.namastays_partner.ui.theme.GradientButton
import com.example.namastays_partner.ui.theme.InterFont
import com.example.namastays_partner.ui.theme.rememberAdaptiveSpacing
import com.example.namastays_partner.utilities.Amenity
import com.example.namastays_partner.viewmodel.VendorOnboardingViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AmenitiesScreen(navController: NavController,
                    viewModel: VendorOnboardingViewModel) {

    val spacing = rememberAdaptiveSpacing()
    val verticalEdgePadding = spacing.edge

    val primaryBlue = Color(0xFF1F3A68)
    val selectedBlue = Color(0xFF274C8E)
    val bgColor = Color(0xFFF3F4F6)
    val cardColor = Color.White

    val scrollState = rememberScrollState()

    val propertyAmenities = listOf(
        Amenity("wifi", "Free WiFi"),
        Amenity("parking", "Parking"),
        Amenity("restaurant", "Restaurant"),
        Amenity("pool", "Swimming Pool"),
        Amenity("gym", "Gym"),
        Amenity("spa", "Spa"),
        Amenity("conference", "Conference Hall"),
        Amenity("garden", "Garden"),
        Amenity("rooftop", "Rooftop"),
        Amenity("bar", "Bar & Lounge"),
        Amenity("pet", "Pet Friendly"),
        Amenity("reception", "24x7 Reception"),
        Amenity("lift", "Lift"),
        Amenity("cctv", "CCTV"),
    )

    val roomAmenities = listOf(
        Amenity("ac", "AC"),
        Amenity("tv", "TV"),
        Amenity("balcony", "Balcony"),
        Amenity("mountain_view", "Mountain View"),
        Amenity("mini_bar", "Mini Bar"),
        Amenity("safe", "Safe"),
        Amenity("hair_dryer", "Hair Dryer"),
        Amenity("bathtub", "Bathtub"),
        Amenity("desk", "Work Desk"),
        Amenity("coffee", "Coffee Maker"),
        Amenity("city_view", "City View"),
        Amenity("wardrobe", "Wardrobe"),
        Amenity("iron", "Iron"),
        Amenity("heater", "Room Heater"),
        Amenity("soundproof", "Soundproof Rooms"),
        Amenity("housekeeping", "Daily Housekeeping")
    )

    val state = viewModel.state
    val selectedAmenities = state.amenities

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {

        // Scrollable content (top part)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .imePadding()
                .padding(horizontal = 20.dp, vertical = verticalEdgePadding)
                .padding(bottom = 140.dp) // reserve space for badge + button
        ) {

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
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

            // Step Indicator
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "STEP 5 OF 7",
                        fontSize = 12.sp,
                        color = primaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFont
                    )
                    Text(
                        "71% Completed",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontFamily = InterFont
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = 0.71f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(50)),
                    color = primaryBlue,
                    trackColor = Color(0xFFE5E7EB)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                "Select Amenities",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFont
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Help guests discover what makes your property special. Select all that apply.",
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                lineHeight = 20.sp,
                fontFamily = InterFont,
                fontWeight = FontWeight.ExtraLight
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Amenities card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    SectionTitle("PROPERTY AMENITIES")
                    AmenitiesGrid(
                        amenities = propertyAmenities,
                        selectedAmenities = selectedAmenities,
                        selectedBlue = selectedBlue,
                        onAmenityClick = { viewModel.toggleAmenity(it) }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle("ROOM AMENITIES")
                    AmenitiesGrid(
                        amenities = roomAmenities,
                        selectedAmenities = selectedAmenities,
                        selectedBlue = selectedBlue,
                        onAmenityClick = { viewModel.toggleAmenity(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

        }

        // Sticky Bottom Section
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(bgColor)
                .navigationBarsPadding()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (selectedAmenities.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFE5E7EB),
                ) {
                    Text(
                        "${selectedAmenities.size} amenities selected",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        fontSize = 13.sp,
                        fontFamily = InterFont
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            GradientButton(
                text = "Continue",
                enabled = selectedAmenities.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate("media_upload_screen")
                }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AmenitiesGrid(
    amenities: List<Amenity>,
    selectedAmenities: List<Amenity>,
    selectedBlue: Color,
    onAmenityClick: (Amenity) -> Unit
) {

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        maxItemsInEachRow = 2
    ) {

        amenities.forEach { amenity ->

            val isSelected = selectedAmenities.any { it.id == amenity.id }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) selectedBlue else Color(0xFFF3F4F6)
                ),
                onClick = { onAmenityClick(amenity) }
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = getAmenityIcon(amenity.id),
                        contentDescription = null,
                        tint = if (isSelected) Color.White else Color.Gray
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        amenity.name,
                        color = if (isSelected) Color.White else Color.Black,
                        fontFamily = InterFont,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF94A3B8),
        fontFamily = InterFont
    )

    Spacer(modifier = Modifier.height(16.dp))
}




fun getAmenityIcon(id: String): ImageVector {
    return when (id) {
        "wifi" -> Icons.Default.Wifi
        "parking" -> Icons.Default.LocalParking
        "restaurant" -> Icons.Default.Restaurant
        "pool" -> Icons.Default.Pool
        "gym" -> Icons.Default.FitnessCenter
        "spa" -> Icons.Default.Spa
        "conference" -> Icons.Default.MeetingRoom
        "garden" -> Icons.Default.Park
        "rooftop" -> Icons.Default.Roofing
        "bar" -> Icons.Default.LocalBar
        "pet" -> Icons.Default.Pets
        "reception" -> Icons.Default.SupportAgent
        "lift" -> Icons.Default.Elevator
        "cctv" -> Icons.Default.Videocam
        "ac" -> Icons.Default.AcUnit
        "tv" -> Icons.Default.Tv
        "balcony" -> Icons.Default.Balcony
        "mountain_view" -> Icons.Default.Landscape
        "mini_bar" -> Icons.Default.LocalDrink
        "safe" -> Icons.Default.Lock
        "hair_dryer" -> Icons.Default.Air
        "bathtub" -> Icons.Default.Bathtub
        "desk" -> Icons.Default.Desk
        "coffee" -> Icons.Default.Coffee
        "city_view" -> Icons.Default.LocationCity
        "wardrobe" -> Icons.Default.Checkroom
        "iron" -> Icons.Default.Iron
        "heater" -> Icons.Default.Whatshot
        "soundproof" -> Icons.Default.HearingDisabled
        "housekeeping" -> Icons.Default.CleaningServices
        else -> Icons.Default.Check
    }
}
