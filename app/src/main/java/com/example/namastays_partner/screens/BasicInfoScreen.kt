package com.example.namastays_partner.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.namastays_partner.ui.theme.GradientButton
import com.example.namastays_partner.ui.theme.InterFont
import com.example.namastays_partner.ui.theme.rememberAdaptiveSpacing
import com.example.namastays_partner.viewmodel.AuthViewModel
import com.example.namastays_partner.viewmodel.VendorOnboardingViewModel

@Composable
fun BasicInfoScreen(
    navController: NavController,
    viewModel: VendorOnboardingViewModel,
    authViewModel: AuthViewModel
) {


    var nameError by rememberSaveable { mutableStateOf<String?>(null) }
    var phoneError by rememberSaveable { mutableStateOf<String?>(null) }
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }


    val primaryBlue = Color(0xFF1F3A68)
    val bgColor = Color(0xFFF3F4F6)
    val cardColor = Color.White

    val spacing = rememberAdaptiveSpacing()
    val verticalEdgePadding = spacing.edge

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
                progress = 0.14f,
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
                "Owner Information",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFont
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Please provide your personal details to create your vendor account.",
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                lineHeight = 20.sp,
                fontFamily = InterFont,
                fontWeight = FontWeight.ExtraLight
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Card Form
        Card(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                // FULL NAME
                Row(){
                    Text("Full Name ", fontWeight = FontWeight.Light, fontFamily = InterFont)
                    Text("*", fontWeight = FontWeight.Light, fontFamily = InterFont, color = Color.Red)
                }

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = viewModel.state.name,
                    onValueChange = {
                        viewModel.updateName(it)
                        nameError = null
                },
                isError = nameError != null,
                placeholder = { Text("e.g. Aayush Aryal", fontFamily = InterFont, fontWeight = FontWeight.ExtraLight) },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                nameError?.let { errorMessage ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = InterFont,
                        fontWeight = FontWeight.ExtraLight
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // PHONE NUMBER
                Row(){
                    Text("Phone Number ", fontWeight = FontWeight.Light, fontFamily = InterFont)
                    Text("*", fontWeight = FontWeight.Light, fontFamily = InterFont, color = Color.Red)
                }


                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = viewModel.state.phone,
                    onValueChange = {
                        viewModel.updatePhone(it)
                        phoneError = null
                    },
                    isError = phoneError != null,
                    placeholder = { Text("XXXXXXXXXX", fontFamily = InterFont, fontWeight = FontWeight.ExtraLight) },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                phoneError?.let { errorMessage ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = InterFont,
                        fontWeight = FontWeight.ExtraLight
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // EMAIL
                Row(){
                    Text("Email Address ", fontWeight = FontWeight.Light, fontFamily = InterFont)
                    Text("*", fontWeight = FontWeight.Light, fontFamily = InterFont, color = Color.Red)


                }
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = viewModel.state.email,
                    onValueChange = {
                        viewModel.updateEmail(it)
                        emailError = null
                    },
                    isError = emailError != null,
                    placeholder = { Text("xxxx@gmail.com", fontFamily = InterFont, fontWeight = FontWeight.ExtraLight) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                emailError?.let { errorMessage ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = InterFont,
                        fontWeight = FontWeight.ExtraLight
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))


        if (authViewModel.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Continue Button (Gradient Version)

        GradientButton(
            text = "Continue",
            modifier = Modifier.padding(horizontal = 20.dp),
            enabled = !authViewModel.isLoading,
            onClick = {

                nameError =
                    if (viewModel.state.name.isBlank())
                        "Full name is required"
                    else null

                phoneError =
                    if (viewModel.state.phone.length != 10)
                        "Phone must be 10 digits"
                    else null

                emailError =
                    if (!Patterns.EMAIL_ADDRESS
                            .matcher(viewModel.state.email)
                            .matches()
                    )
                        "Invalid email address"
                    else null

                if (nameError == null &&
                    phoneError == null &&
                    emailError == null
                ) {
                    authViewModel.sendOtp("+977${viewModel.state.phone}")
                }
            }
        )
        Spacer(Modifier.height(40.dp))

        LaunchedEffect(authViewModel.otpSent) {

            if (authViewModel.otpSent) {
                authViewModel.resetOtpState()

                navController.navigate("otp_screen/${viewModel.state.phone}")
            }
        }

        authViewModel.errorMessage?.let {

            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}


//@Composable
//@Preview(showBackground = true,
//    device = Devices.NEXUS_6)
//fun BasicInfoScreenPreview(){
//    BasicInfoScreen(
//        navController = rememberNavController(),
//        viewModel = { TODO() }
//    )
//}