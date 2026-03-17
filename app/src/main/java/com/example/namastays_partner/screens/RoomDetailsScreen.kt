package com.example.namastays_partner.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.namastays_partner.model.Room
import com.example.namastays_partner.ui.theme.GradientButton
import com.example.namastays_partner.ui.theme.InterFont
import com.example.namastays_partner.ui.theme.rememberAdaptiveSpacing
import com.example.namastays_partner.viewmodel.VendorOnboardingViewModel


@Composable
fun RoomDetailsScreen(
    navController: NavController,
    viewModel: VendorOnboardingViewModel
) {

    val primaryBlue = Color(0xFF1F3A68)
    val bgColor = Color(0xFFF3F4F6)

    val spacing = rememberAdaptiveSpacing()
    val verticalEdgePadding = spacing.edge

    var roomsError by remember { mutableStateOf<String?>(null) }

    val state = viewModel.state
    val rooms = state.rooms
    val propertyType = state.propertyType

    var showDialog by remember { mutableStateOf(false) }
    var editingRoom by remember { mutableStateOf<Room?>(null) }

    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
           // .verticalScroll(scrollState)
            .imePadding()
            .padding(vertical = verticalEdgePadding)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // TOP BAR
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier
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

            Spacer(modifier = Modifier.height(20.dp))


            // Step Indicator
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "STEP 4 OF 7",
                        fontSize = 12.sp,
                        color = primaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFont
                    )
                    Text(
                        "57% Completed",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontFamily = InterFont
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = 0.57f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(50)),
                    color = primaryBlue,
                    trackColor = Color(0xFFE5E7EB)
                )
            }




            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Room Details",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFont

            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                "Add the types of rooms available in your property.",
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = InterFont,
                fontWeight = FontWeight.ExtraLight

            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    //  Scrollable Content
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(20.dp)
                            .padding(bottom = 120.dp) // extra space so FAB doesn't cover content
                    ) {

                        Text(
                            "Your Rooms",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = InterFont

                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            "${rooms.size} categories added",
                            fontSize = 13.sp,
                            color = Color.Gray,
                            fontFamily = InterFont,
                            fontWeight = FontWeight.ExtraLight
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        rooms.forEach { room ->
                            RoomCard(
                                room = room,
                                onEdit = {
                                    editingRoom = room
                                    showDialog = true
                                }
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                        }
                    }

                    // Fixed FAB
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 24.dp, bottom = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        FloatingActionButton(
                            onClick = {
                                editingRoom = null
                                showDialog = true
                            },
                            containerColor = Color(0xFF2D5BFF),
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Room",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Add Room",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color(0xFF2D5BFF),
                            fontFamily = InterFont

                        )
                    }
                }
            }

            roomsError?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.ExtraLight
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Continue Button

            Spacer(modifier = Modifier.height(20.dp))

            GradientButton(
                text = "Continue",
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    if (rooms.isEmpty()) {
                        roomsError = "Please add at least one room category"
                    } else {
                        roomsError = null
                        navController.navigate("amenities_screen")
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

        }


    }

    if (showDialog) {
        AddRoomDialog(
            propertyType = propertyType,
            existingRoom = editingRoom,
            onDismiss = { showDialog = false },
            onSave = { room ->
                if (editingRoom == null) {
                    viewModel.addRoom(room)
                } else {
                    viewModel.updateRoom(room.copy(id = editingRoom!!.id))
                }
                showDialog = false
            },
            onDelete = {
                editingRoom?.let { room ->
                    viewModel.deleteRoom(room.id)                }
                showDialog = false
            }
        )
    }
}

@Composable
fun RoomCard(room: Room, onEdit: () -> Unit) {

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                Text(
                    text = room.category,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = InterFont

                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFE8F0FE))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        "₹${room.pricePerNight} / night",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2563EB),
                        fontFamily = InterFont

                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Person, null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Max ${room.maxGuests} guests", fontSize = 13.sp, color = Color.Gray, fontFamily = InterFont, fontWeight = FontWeight.ExtraLight)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Bed, null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(room.bedType, fontSize = 13.sp, color = Color.Gray, fontFamily = InterFont, fontWeight = FontWeight.ExtraLight)
                    }
                }

                Text(
                    "${room.totalRooms} room(s) available",
                    fontSize = 12.sp,
                    color = Color(0xFF6B7280),
                    fontFamily = InterFont,
                    fontWeight = FontWeight.ExtraLight

                )
            }

            IconButton(onClick = onEdit) {
                Icon(Icons.Outlined.Edit, null, tint = Color(0xFF2563EB))
            }
        }
    }
}

@Composable
fun AddRoomDialog(
    propertyType: String,
    existingRoom: Room?,
    onDismiss: () -> Unit,
    onSave: (Room) -> Unit,
    onDelete: () -> Unit
) {

    val categories = if (propertyType == "Hotel")
        listOf(
            "Deluxe Room",
            "Super Deluxe Room",
            "Executive Suite",
            "Family Suite"
        )
    else
        listOf(
            "Private Bedroom",
            "Shared Bedroom",
            "Open Sleeping Area",
            "Dormitory Bed"
        )

    val guestOptions = (1..10).map { it.toString() }

    val bedOptions = listOf(
        "Single Bed",
        "Double Bed",
        "Queen Size Bed",
        "King Size Bed"
    )

    var category by remember { mutableStateOf(existingRoom?.category ?: categories.first()) }
    var guests by remember { mutableStateOf(existingRoom?.maxGuests?.toString() ?: "1") }
    var bed by remember { mutableStateOf(existingRoom?.bedType ?: bedOptions.first()) }
    var totalRooms by remember { mutableStateOf(existingRoom?.totalRooms?.toString() ?: "1") }
    var price by remember { mutableStateOf(existingRoom?.pricePerNight?.toString() ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                Text(
                    if (existingRoom == null) "Add Room" else "Edit Room",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFont

                )

                Spacer(modifier = Modifier.height(20.dp))

                DropdownField("Room Category", category, categories) { category = it }
                Spacer(modifier = Modifier.height(12.dp))

                DropdownField("Maximum Guests", guests, guestOptions) { guests = it }
                Spacer(modifier = Modifier.height(12.dp))

                DropdownField("Bed Type", bed, bedOptions) { bed = it }
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = totalRooms,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) totalRooms = it
                    },
                    label = { Text("Number of Rooms Available", fontFamily = InterFont, fontWeight = FontWeight.ExtraLight) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = price,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) price = it
                    },
                    label = { Text("Price per Night (₹)", fontFamily = InterFont, fontWeight = FontWeight.ExtraLight) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onSave(
                            Room(
                                id = existingRoom?.id ?: 0,
                                category = category,
                                maxGuests = guests.toIntOrNull() ?: 1,
                                bedType = bed,
                                totalRooms = totalRooms.toIntOrNull() ?: 1,
                                pricePerNight = price.toIntOrNull() ?: 0
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (existingRoom == null) "Add Room" else "Save Changes", fontFamily = InterFont, fontWeight = FontWeight.ExtraLight)
                }

                if (existingRoom != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = onDelete,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Delete Room", color = Color.Red, fontFamily = InterFont
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,

            // Label Style
            label = {
                Text(
                    text = label,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = 14.sp
                )
            },

            // Selected Value Style
            textStyle = TextStyle(
                fontFamily = InterFont,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 15.sp,
                color = Color.Black
            ),

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },

            shape = RoundedCornerShape(14.dp),

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2563EB),
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = Color(0xFF2563EB)
            ),

            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->

                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            fontFamily = InterFont,
                            fontWeight = FontWeight.ExtraLight,
                            fontSize = 14.sp
                        )
                    },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

