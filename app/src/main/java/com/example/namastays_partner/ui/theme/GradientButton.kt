package com.example.namastays_partner.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showIcon: Boolean = true,
    brush: Brush = Brush.horizontalGradient(
        listOf(
            Color(0xFF3B82F6),
            Color(0xFF2563EB)
        )
    )
) {
    Box(
        modifier = modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        // Soft shadow layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(y = 6.dp)
                .background(
                    Color(0xFF2563EB).copy(alpha = 0.18f),
                    RoundedCornerShape(50.dp)
                )
        )

        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            contentPadding = PaddingValues()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = brush,
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = text,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFont
                    )

                    if (showIcon) {
                        Spacer(Modifier.width(8.dp))

                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}