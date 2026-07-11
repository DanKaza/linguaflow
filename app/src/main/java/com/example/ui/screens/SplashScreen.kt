package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandSecondary
import com.example.ui.theme.SoftLavenderBg

@Composable
fun SplashScreen() {
    // Breathing scale animation for the logo
    val infiniteTransition = rememberInfiniteTransition(label = "breath")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Shimmering progress width animation
    var progress by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(2500, easing = EaseOutQuad)
        ) { value, _ ->
            progress = value
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(SoftLavenderBg, Color(0xFFFAFAFA))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Breathing Kanji Logo Box
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .scale(scale)
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFCAC4D0),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "語",
                    fontSize = 54.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // App Name with high tracking
            Text(
                text = "LinguaFlow",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = BrandPrimary,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "Belajar Bahasa Jepang dengan Tenang.",
                fontSize = 16.sp,
                color = BrandSecondary.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(56.dp))

            // Progress loading bar
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(200.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(Color(0xFFCAC4D0), RoundedCornerShape(100.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress)
                            .background(BrandPrimary.copy(alpha = 0.8f), RoundedCornerShape(100.dp))
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "MEMUAT SESI",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandSecondary.copy(alpha = 0.5f),
                    letterSpacing = 2.sp
                )
            }
        }

        // Attribution
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        ) {
            Text(
                text = "by LinguaFlow Team",
                fontSize = 12.sp,
                color = BrandSecondary.copy(alpha = 0.4f),
                letterSpacing = 1.sp
            )
        }
    }
}
