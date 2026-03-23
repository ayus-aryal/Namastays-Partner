package com.example.namastays_partner.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.namastays_partner.R
import com.example.namastays_partner.ui.theme.GradientButton
import com.example.namastays_partner.ui.theme.InterFont
import com.example.namastays_partner.ui.theme.rememberAdaptiveSpacing


private val Background = Color(0xFFF3F4F6)
private val Blue = Color(0xFF2563EB)
private val WhitishBlue = Color(0xFFF0F8FF)


@Composable
fun WelcomeScreen(navController: NavController) {

    val spacing = rememberAdaptiveSpacing()

    val verticalEdgePadding = spacing.edge
    val largeSpacing = spacing.large
    val mediumSpacing = spacing.medium
    val smallSpacing = spacing.small

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = verticalEdgePadding)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Logo
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Home,
                contentDescription = null,
                tint = Blue,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                "Namastays",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Blue,
                fontFamily = InterFont
            )
        }

        Spacer(Modifier.height(mediumSpacing))

        // Badge
        Surface(
            color = Blue,
            shape = RoundedCornerShape(50)
        ) {
            Text(
                "For Property Owners",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                fontFamily = InterFont
            )
        }

        Spacer(Modifier.height(smallSpacing))

        // Heading
        Text(
            "List Your Property\non Namastays",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            color = Color(0xFF111827),
            lineHeight = 34.sp,
            fontFamily = InterFont
        )

        Spacer(Modifier.height(mediumSpacing))

        // Subtitle
        Text(
            "Join thousands of property owners\nearning more with smart hosting tools.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color(0xFF6B7280),
            lineHeight = 22.sp,
            fontFamily = InterFont,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(smallSpacing))

        FeatureItem(
            Icons.Default.TrendingUp,
            "More Bookings",
            "Reach millions of travelers worldwide"
        )

        Spacer(Modifier.height(smallSpacing))

        FeatureItem(
            Icons.Default.Shield,
            "Secure Payments",
            "Get paid safely with our escrow system"
        )

        Spacer(Modifier.height(smallSpacing))

        FeatureItem(
            Icons.Default.Settings,
            "Easy Management",
            "Manage bookings from your phone anytime"
        )

        Spacer(Modifier.height(largeSpacing))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StepChip(1, "Add\nProperty", true, Modifier.weight(1f))
            StepChip(2, "Add\nRooms", true, Modifier.weight(1f))
            StepChip(3, "Get\nVerified", true, Modifier.weight(1f))
        }

        Spacer(Modifier.height(mediumSpacing))

        GradientButton(
            text = "Start Listing",
            onClick = { navController.navigate("basic_info_screen") }
        )

        Spacer(Modifier.height(smallSpacing))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Already have an account? ",
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                fontFamily = InterFont,
                fontWeight = FontWeight.SemiBold
            )
            TextButton(
                onClick = {
                    navController.navigate("login_screen")
                },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    "Log in",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Blue,
                    fontFamily = InterFont
                )
            }
        }
    }
}



@Composable
fun FeatureItem(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {

            Box(
                modifier = Modifier
                    .width(5.dp)
                    .fillMaxHeight()
                    .background(
                        Blue,
                        RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                    )
            )

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(WhitishBlue, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = Blue)
                }

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFont,

                        )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280),
                        fontFamily = InterFont,
                        fontWeight = FontWeight.ExtraLight


                        )
                }
            }
        }
    }
}




@Composable
fun StepChip(
    number: Int,
    label: String,
    active: Boolean,
    modifier: Modifier
) {

    val bgColor = if (active) Blue else Color(0xFFE5E7EB)
    val textColor = if (active) Color.White else Color(0xFF9CA3AF)

    Row(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(50))
            .padding(horizontal = 18.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .size(22.dp)
                .background(
                    if (active) Color.White else Color(0xFF9CA3AF),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "$number",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (active) Blue else Color.White
            )
        }

        Spacer(Modifier.width(6.dp))

        Text(
            label,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 16.sp,
            fontFamily = InterFont
            )
    }
}



@Composable
@Preview(showBackground = true)
fun Welcomescreenpreview(){
    WelcomeScreen(navController = rememberNavController())
}