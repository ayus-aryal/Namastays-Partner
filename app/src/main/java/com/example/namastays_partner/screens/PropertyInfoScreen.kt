package com.example.namastays_partner.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.MapsHomeWork
import androidx.compose.material.icons.filled.Villa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Devices
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.namastays_partner.ui.theme.GradientButton
import com.example.namastays_partner.ui.theme.InterFont
import com.example.namastays_partner.ui.theme.rememberAdaptiveSpacing
import com.example.namastays_partner.viewmodel.VendorOnboardingViewModel

//YES--take it out of the card

private val propertyTypes = listOf(
    "Hotel",
    "Homestay"
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyInfoScreen(navController: NavController,
                       viewModel: VendorOnboardingViewModel){

    var propertyNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var propertyTypeError by rememberSaveable { mutableStateOf<String?>(null) }
    var propertyDescriptionError by rememberSaveable { mutableStateOf<String?>(null) }

    var expanded by remember { mutableStateOf(false) }

    val maxChars = 500

    val spacing = rememberAdaptiveSpacing()
    val verticalEdgePadding = spacing.edge

    val scrollState = rememberScrollState()


    val primaryBlue = Color(0xFF1F3A68)
    val bgColor = Color(0xFFF3F4F6)
    val cardColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .verticalScroll(scrollState)
            .imePadding()
            .padding(vertical = verticalEdgePadding),

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
                        "STEP 3 OF 7",
                        fontSize = 12.sp,
                        color = primaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFont
                    )
                    Text(
                        "43% Completed",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontFamily = InterFont
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = 0.43f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(50)),
                    color = primaryBlue,
                    trackColor = Color(0xFFE5E7EB)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Heading
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                Text(
                    "Property Information",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFont
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Please provide your property details to create your vendor account.",
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
            ){
                Column(modifier = Modifier.padding(20.dp)){

                    // PROPERTY NAME
                    Row(){
                        Text("Property Name ", fontWeight = FontWeight.Light, fontFamily = InterFont)
                        Text("*", fontWeight = FontWeight.Light, fontFamily = InterFont, color = Color.Red)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = viewModel.state.propertyName,
                        onValueChange = {
                            viewModel.updatePropertyName(it)
                            propertyNameError = null
                        },
                        isError = propertyNameError != null,
                        placeholder = { Text("e.g. SK Hotel", fontFamily = InterFont, fontWeight = FontWeight.ExtraLight) },
                        leadingIcon = { Icon(Icons.Default.Villa, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )

                    propertyNameError?.let {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(it, color = Color.Red, fontSize = 12.sp, fontFamily = InterFont, fontWeight = FontWeight.ExtraLight)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // PROPERTY TYPE
                    Row(){
                        Text("Property Type ", fontWeight = FontWeight.Light, fontFamily = InterFont)
                        Text("*", fontWeight = FontWeight.Light, fontFamily = InterFont, color = Color.Red)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = viewModel.state.propertyType,
                            onValueChange = {
                                viewModel.updatePropertyType(it)
                            },
                            readOnly = true,
                            isError = propertyTypeError != null,
                            placeholder = { Text("Select Property Type", fontFamily = InterFont, fontWeight = FontWeight.ExtraLight) },
                            leadingIcon = { Icon(Icons.Default.Villa, contentDescription = null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            containerColor = Color.White,
                            tonalElevation = 2.dp

                        ) {
                            propertyTypes.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type) },
                                    onClick = {
                                        viewModel.updatePropertyType(type)
                                        propertyTypeError = null
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    propertyTypeError?.let {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(it, color = Color.Red, fontSize = 12.sp, fontFamily = InterFont, fontWeight = FontWeight.ExtraLight)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // DESCRIPTION
                    Row(){
                        Text("Description ", fontWeight = FontWeight.Light, fontFamily = InterFont)
                        Text("*", fontWeight = FontWeight.Light, fontFamily = InterFont, color = Color.Red)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = viewModel.state.propertyDescription,
                        onValueChange = {
                            viewModel.updatePropertyDescription(it)
                            propertyDescriptionError = null
                        },
                        isError = propertyDescriptionError != null,
                        placeholder = { Text("Describe your property, its unique features, nearby attractions...", fontFamily = InterFont, fontWeight = FontWeight.ExtraLight) },
                        leadingIcon = { Icon(Icons.Default.MapsHomeWork, contentDescription = null) },
                        singleLine = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        Text("${viewModel.state.propertyDescription.length}/500",
                            fontSize = 12.sp,
                            fontFamily = InterFont,
                            color = if(viewModel.state.propertyDescription.length == 500) Color.Red else Color(0xFF6B7280))
                    }

                    propertyDescriptionError?.let {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(it, color = Color.Red, fontSize = 12.sp, fontFamily = InterFont, fontWeight = FontWeight.ExtraLight)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // YEAR
                    Text("Year Established ", fontWeight = FontWeight.Light, fontFamily = InterFont)

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = viewModel.state.yearEstablished,
                        onValueChange = {
                            viewModel.updateYearEstablished(it)
                        },
                        placeholder = { Text("e.g. 2020", fontFamily = InterFont, fontWeight = FontWeight.ExtraLight) },
                        leadingIcon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))


                    Card(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ){
                        Row(modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = Color(0xFF2563EB) )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                "Accurate property details help travelers find and book your listings more easily.",
                                fontSize = 13.sp,
                                color = Color(0xFF1E3A8A),
                                fontFamily = InterFont,
                                fontWeight = FontWeight.ExtraLight
                            )
                        }
                    }




                }
            }

        // Continue Button
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {

            GradientButton(
                text = "Continue",
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    propertyNameError =
                        if (viewModel.state.propertyName.isBlank())
                            "Property name is required"
                        else null

                    propertyTypeError =
                        if (viewModel.state.propertyType.isBlank())
                            "Property type is required"
                        else null

                    propertyDescriptionError =
                        if (viewModel.state.propertyDescription.isBlank())
                            "Description is required"
                        else null

                    if (propertyNameError == null &&
                        propertyTypeError == null &&
                        propertyDescriptionError == null
                    ) {
                        navController.navigate("room_details_screen")
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


    }
}
