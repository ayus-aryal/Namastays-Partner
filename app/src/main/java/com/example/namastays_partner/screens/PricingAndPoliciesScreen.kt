package com.example.namastays_partner.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.namastays_partner.ui.theme.GradientButton
import com.example.namastays_partner.ui.theme.InterFont
import com.example.namastays_partner.ui.theme.rememberAdaptiveSpacing
import com.example.namastays_partner.utilities.formatTo12Hour
import com.example.namastays_partner.viewmodel.VendorOnboardingViewModel


//YES

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PricingAndPoliciesScreen(navController: NavController,
                             viewModel: VendorOnboardingViewModel) {

    val state = viewModel.state

    var expanded by remember { mutableStateOf(false) }

    var checkInError by remember { mutableStateOf<String?>(null) }
    var checkOutError by remember { mutableStateOf<String?>(null) }

    val primaryBlue = Color(0xFF1F3A68)
    val bgColor = Color(0xFFF3F4F6)
    val cardColor = Color.White

    val policies = listOf(
        "Flexible (Full refund 1 day prior)",
        "Moderate (Full refund 5 days prior)",
        "Strict (50% refund until 1 week prior)",
        "Non-refundable"
    )

    val listState = rememberLazyListState()

    val spacing = rememberAdaptiveSpacing()
    val verticalEdgePadding = spacing.edge

    val scrollState = rememberScrollState()


    fun validate(): Boolean {

        var isValid = true

        // Check-in validation
        if (state.policies.checkInTime.isBlank()) {
            checkInError = "Check-in time is required"
            isValid = false
        } else {
            checkInError = null
        }

        // Check-out validation
        if (state.policies.checkOutTime.isBlank()) {
            checkOutError = "Check-out time is required"
            isValid = false
        } else if (state.policies.checkOutTime == state.policies.checkInTime) {
            checkOutError = "Check-out cannot be same as check-in"
            isValid = false
        } else {
            checkOutError = null
        }

        return isValid
    }

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
                Spacer(Modifier.width(16.dp))
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
                        "STEP 7 OF 7",
                        fontSize = 12.sp,
                        color = primaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFont
                    )
                    Text(
                        "99% Completed",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontFamily = InterFont
                    )
                }

                Spacer(Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = 0.99f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(50)),
                    color = primaryBlue,
                    trackColor = Color(0xFFE5E7EB)
                )
            }

            Spacer(Modifier.height(28.dp))

            // Heading
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    "Pricing & Policies",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFont
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Set your property rules and cancellation policy.",
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                    lineHeight = 20.sp,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.ExtraLight
                )
            }

            Spacer(Modifier.height(24.dp))

            // Main Card
            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            TimePickerField("Check-in Time", state.policies.checkInTime) {
                                viewModel.updateCheckInTime(it)
                            }
                            checkInError?.let {
                                Text(
                                    text = it,
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    fontFamily = InterFont,
                                    fontWeight = FontWeight.ExtraLight
                                )
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            TimePickerField("Check-out Time", state.policies.checkOutTime) {
                                viewModel.updateCheckOutTime(it)
                            }

                            checkOutError?.let {
                                Text(
                                    text = it,
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    fontFamily = InterFont,
                                    fontWeight = FontWeight.ExtraLight
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    RequiredLabel("Cancellation Policy")
                    Spacer(Modifier.height(6.dp))

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = state.policies.cancellationPolicy,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            policies.forEach { policy ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            policy,
                                            color = if (policy == state.policies.cancellationPolicy)
                                                Color.White else Color.Black
                                        )
                                    },
                                    onClick = {
                                        viewModel.updateCancellationPolicy(policy)
                                        expanded = false
                                    },
                                    modifier = Modifier.background(
                                        if (policy == state.policies.cancellationPolicy)
                                            Color(0xFF7A7A7A)
                                        else Color.Transparent
                                    )
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    PolicyTextField(
                        label = "Extra Guest Price",
                        value = state.policies.extraGuestPrice,
                        placeholder = "e.g. Rs. 200"
                    ) {
                        viewModel.updateExtraGuestPrice(it)
                    }

                    Spacer(Modifier.height(20.dp))

                    PolicySwitchRow("Smoking Allowed", state.policies.smokingAllowed) {
                        viewModel.updateSmokingAllowed(it)
                    }

                    PolicySwitchRow("Children Allowed", state.policies.childrenAllowed) {
                        viewModel.updateChildrenAllowed(it)
                    }

                    PolicySwitchRow("Pets Allowed", state.policies.petsAllowed) {
                        viewModel.updatePetsAllowed(it)
                    }

                    PolicySwitchRow("Breakfast Included", state.policies.breakfastIncluded) {
                        viewModel.updateBreakfastIncluded(it)
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            GradientButton(
                text = "Continue",
                onClick = {
                    if (validate()) {
                        navController.navigate("review_screen")
                    }
                }
            )
        }

        Spacer(Modifier.height(24.dp))


    }
}


@Composable
fun PolicyTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {

    Column {
        Text(
            text = label,
            fontFamily = InterFont,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.ExtraLight
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        )
    }
}

@Composable
fun PolicySwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            fontFamily = InterFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = Color(0xFF374151)
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF22C55E),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFD1D5DB)
            )
        )
    }
}

@Composable
fun RequiredLabel(text: String) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = text,
            fontFamily = InterFont,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            color = Color(0xFF374151)
        )

        Text(
            text = " *",
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(
    label: String,
    value: String,
    onTimeSelected: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    RequiredLabel(label)
    Spacer(Modifier.height(6.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            enabled = false, // important
            placeholder = { Text("Select time") },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (showDialog) {

        val timePickerState = rememberTimePickerState()

        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val formatted = formatTo12Hour(
                            timePickerState.hour,
                            timePickerState.minute
                        )
                        onTimeSelected(formatted)
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}

