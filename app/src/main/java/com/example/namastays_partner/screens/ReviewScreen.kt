package com.example.namastays_partner.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.namastays_partner.model.Room
import com.example.namastays_partner.ui.theme.GradientButton
import com.example.namastays_partner.ui.theme.InterFont
import com.example.namastays_partner.utilities.Amenity
import com.example.namastays_partner.utilities.PhotoCategoryState
import com.example.namastays_partner.utilities.VendorOnboardingState
import com.example.namastays_partner.viewmodel.VendorOnboardingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navController: NavController,
    viewModel: VendorOnboardingViewModel
) {

    val state = viewModel.state
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.submissionSuccess) {
        if (viewModel.submissionSuccess) {
            navController.navigate("waiting_screen") {
                popUpTo("review_screen") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7F9))
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(bottom = 100.dp)
        ) {

            TopAppBar(
                title = { Text("Review & Submit", fontFamily = InterFont, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            PropertyHeroCard(state)

            Spacer(Modifier.height(16.dp))

            BasicInfoSection(state)
            LocationSection(state)
            PropertyDetailsSection(state)

            RoomsSection(
                rooms = state.rooms,
                roomPhotos = state.photoCategories
                    .find { it.title.equals("Room Photos", true) }
                    ?.photos ?: emptyList()
            )

            AmenitiesSection(
                amenities = state.amenities
            )

            PoliciesSection(state)

            PhotosSection(
                photoCategories = state.photoCategories
            )
            Spacer(Modifier.height(32.dp))
        }



        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ){
            GradientButton(
                text = if(viewModel.isLoading) "" else "Submit for Review",
                onClick = {
                    println("Button Pressed")
                    viewModel.submitVendorOnboarding(context)
                },
                enabled = !viewModel.isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            if(viewModel.isLoading){
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }
        }

        // Navigate on success

    }
}

@Composable
fun RoomsSection(
    rooms: List<Room>,
    roomPhotos: List<Uri>
) {

    SectionCard(title = "Rooms & Spaces (${rooms.size})") {

        LazyRow(    contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {


            itemsIndexed(rooms) { index, room ->

                val image = roomPhotos.getOrNull(index)

                RoomCard(
                    room = room,
                    imageUri = image
                )
            }
        }
    }
}


@Composable
fun RoomCard(room: Room, imageUri: Uri?) {

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.width(280.dp)
    ) {

        Column {

            // Full width image
            imageUri?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                // Room Title
                Text(
                    text = room.category,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = InterFont
                )

                // Guests + Bed
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            "${room.maxGuests}",
                            fontSize = 13.sp,
                            color = Color.Gray,
                            fontFamily = InterFont
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Bed,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            room.bedType,
                            fontSize = 13.sp,
                            color = Color.Gray,
                            fontFamily = InterFont
                        )
                    }
                }

                // Price
                Text(
                    text = "₹${room.pricePerNight} / night",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2563EB),
                    fontFamily = InterFont
                )

                // Rooms available
                Text(
                    text = "${room.totalRooms} room(s) available",
                    fontSize = 12.sp,
                    color = Color(0xFF6B7280),
                    fontFamily = InterFont,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable
fun AmenitiesSection(amenities: List<Amenity>) {

    SectionCard(title = "Amenities") {

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            items(amenities) { amenity ->

                AssistChip(
                    onClick = {},
                    label = { Text(amenity.name) },
                    leadingIcon = {
                        Icon(
                            imageVector = getAmenityIcon(amenity.id),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun PoliciesSection(state: VendorOnboardingState) {

    SectionCard(title = "Policies & Rules") {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelValue("CHECK-IN", state.policies.checkInTime)
            LabelValue("CHECK-OUT", state.policies.checkOutTime)
        }

        Divider(
            Modifier.padding(vertical = 12.dp),
            color = Color(0xFFE5E7EB)
        )

        PolicyRow("Smoking Allowed", state.policies.smokingAllowed)
        PolicyRow("Children Allowed", state.policies.childrenAllowed)
        PolicyRow("Pets Allowed", state.policies.petsAllowed)
        PolicyRow("Breakfast Included", state.policies.breakfastIncluded)

        Divider(
            Modifier.padding(vertical = 12.dp),
            color = Color(0xFFE5E7EB)
        )

        LabelValue("CANCELLATION", state.policies.cancellationPolicy)
    }
}

@Composable
fun PhotosSection(photoCategories: List<PhotoCategoryState>) {

    SectionCard(title = "All Photos") {

        photoCategories.forEach { category ->

            if (category.photos.isNotEmpty()) {

                Text(
                    category.title,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                    items(category.photos) { uri ->

                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun PropertyHeroCard(state: VendorOnboardingState) {

    val coverImage = state.photoCategories
        .flatMap { it.photos }
        .firstOrNull()

    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)

    ) {

        Column {

            coverImage?.let {
                Box {

                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )

                    Surface(
                        color = Color.White.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Text(
                            state.propertyType,
                            fontFamily = InterFont,
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            ),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Column(Modifier.padding(20.dp)) {

                Text(
                    state.propertyName,
                    fontSize = 20.sp,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "${state.address.city}, ${state.address.country}",
                        fontFamily = InterFont,
                        color = Color.Gray,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@Composable
fun BasicInfoSection(state: VendorOnboardingState) {

    SectionCard(title = "Basic Information") {
        LabelValue("FULL NAME", state.name)
        LabelValue("PHONE NUMBER", state.phone)
        LabelValue("EMAIL", state.email)
    }
}

@Composable
fun LocationSection(state: VendorOnboardingState) {

    SectionCard(title = "Location Details") {

        LabelValue("ADDRESS", state.address.address)

        Row(Modifier.fillMaxWidth()) {

            Column(Modifier.weight(1f)) {
                LabelValue("CITY", state.address.city)
            }

            Column(Modifier.weight(1f)) {
                LabelValue("STATE/REGION", state.address.state)
            }
        }

        Row(Modifier.fillMaxWidth()) {

            Column(Modifier.weight(1f)) {
                LabelValue("POSTAL CODE", state.address.postalCode)
            }

            Column(Modifier.weight(1f)) {
                LabelValue("COUNTRY", state.address.country)
            }
        }
    }
}

@Composable
fun PropertyDetailsSection(state: VendorOnboardingState) {

    SectionCard(title = "Property Details") {

        Row(Modifier.fillMaxWidth()) {

            Column(Modifier.weight(1f)) {
                LabelValue("PROPERTY TYPE", state.propertyType)
            }

            Column(Modifier.weight(1f)) {
                LabelValue("YEAR ESTABLISHED", state.yearEstablished)
            }
        }

        Divider(
            Modifier.padding(vertical = 12.dp),
            color = Color(0xFFE5E7EB)
        )

        LabelValue("DESCRIPTION", state.propertyDescription)
    }
}

@Composable
fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(Modifier.padding(20.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = InterFont
                )

                Icon(Icons.Outlined.Edit, contentDescription = null)
            }

            Spacer(Modifier.height(16.dp))

            content()

        }
    }
}

@Composable
fun LabelValue(label: String, value: String) {

    Column(Modifier.padding(bottom = 16.dp)) {

        Text(
            label,
            fontSize = 12.sp,
            color = Color(0xFF6B7280),
            fontWeight = FontWeight.ExtraLight,
            fontFamily = InterFont
        )

        Spacer(Modifier.height(4.dp))

        Text(
            value.ifEmpty { "-" },
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraLight,
            fontFamily = InterFont
        )
    }
}


@Composable
fun PolicyRow(label: String, isAllowed: Boolean) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        StatusPill(isAllowed)
    }
}


@Composable
fun StatusPill(isAllowed: Boolean) {

    val bgColor = if (isAllowed) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
    val textColor = if (isAllowed) Color(0xFF2E7D32) else Color(0xFFC62828)
    val icon = if (isAllowed) Icons.Default.Check else Icons.Default.Close
    val text = if (isAllowed) "YES" else "NO"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {

        Box(
            modifier = Modifier
                .size(16.dp)
                .background(textColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(10.dp)
            )
        }

        Spacer(Modifier.width(6.dp))

        Text(
            text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}