package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.AppScreen
import com.example.ui.MainViewModel
import com.example.ui.theme.SoftLavenderBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var tempName by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top section with Logo & App identity
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.42f)
                .background(SoftLavenderBg),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Hotlinked LinguaFlow logo from HTML
                AsyncImage(
                    model = "https://lh3.googleusercontent.com/aida/AP1WRLv1O3UFhwjRpcUyJXVHOZOSyh56U2HhTWy4pkgpi00s-TX9l1Y8BvFwcNFVbqRog4knBh5DrNdDKQm-JH8o-3PO02FUamcfwE7SePJARWez8gHeOrWxPwZzFdXqHv3dPzKfqXeHpan-kERhttkLVzdo76oHNoEze4gII5HcpLrbV7m4aOIilgeCti4IAdpW9ArIBpmYMtRgiFeLmZ0srXrAzxIrDKj8oHAcGsazxDnOOwwppRcgvgJQzwQ",
                    contentDescription = "LinguaFlow Logo",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "LinguaFlow",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = (-0.5).sp
                )
            }

            // Atmosphere ambient blobs
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFA78BFA).copy(alpha = 0.08f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        // Bottom section with Greeting and Actions
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.58f)
                .border(
                    width = 1.dp,
                    color = Color(0xFFCAC4D0),
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                // Greeting text
                Text(
                    text = "Mulai perjalanan belajarmu",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1D1B20),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Masukkan namamu atau langsung mulai sebagai tamu",
                    fontSize = 14.sp,
                    color = Color(0xFF49454F),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Optional name entry
                OutlinedTextField(
                    value = tempName,
                    onValueChange = { tempName = it },
                    placeholder = { Text("Tulis namamu di sini (opsional)...") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color(0xFFCAC4D0),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Primary Button (Guest Start)
                Button(
                    onClick = {
                        viewModel.setUserName(tempName)
                        viewModel.setScreen(AppScreen.MAIN)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(100.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "Mulai Belajar (Tamu)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Secondary Button (Sign in / Sign up)
                OutlinedButton(
                    onClick = {
                        viewModel.setUserName(if (tempName.trim().isEmpty()) "Asep" else tempName)
                        viewModel.setScreen(AppScreen.MAIN)
                    },
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "Masuk / Daftar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Divider
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color(0xFFCAC4D0))
                    )
                    Text(
                        text = "atau",
                        fontSize = 12.sp,
                        color = Color(0xFF49454F),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color(0xFFCAC4D0))
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Social Rows (Google & Apple hotlinks)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Google button
                    OutlinedButton(
                        onClick = {
                            viewModel.setUserName("Google User")
                            viewModel.setScreen(AppScreen.MAIN)
                        },
                        border = BorderStroke(1.dp, Color(0xFFCAC4D0)),
                        shape = RoundedCornerShape(100.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF49454F)),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBMvXh6A_PYHmOJVLvd5N2v4bQYHQyL1dQu2U6oCtGS0Wmu8ika-Eb7ceDoKM-HW-itLDlKjV5HlwshyVCP5qbr3nhmqg2OhKOt6971C2sySTNB6PTmcAsPm4thZFd8whPU1lkAP-bhp3ASLAdOMN6ix8KR2LDVyJyfQ03CCVpQqfb6izvT1A9_Rf6KzPrwXUpFvI9iDfPw9rbwTb2oaLoYdwAE_cTjW0kjoOAClAL4mAY2OBkCTQuR1EmXwFMpOVA65PI8SYv83PlC",
                                contentDescription = "Google Logo",
                                modifier = Modifier.size(20.dp)
                            )
                            Text("Google", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        }
                    }

                    // Apple button
                    OutlinedButton(
                        onClick = {
                            viewModel.setUserName("Apple User")
                            viewModel.setScreen(AppScreen.MAIN)
                        },
                        border = BorderStroke(1.dp, Color(0xFFCAC4D0)),
                        shape = RoundedCornerShape(100.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF49454F)),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuDpinzqb55-mKxg0T79n-hfCChJ3f9EYcfQ00JrN7bb5nXGUh_aRW0gYulybnrAmskjql4-2Ntp_z5qEWXwrLNZN9V2cCNZnhWXCF2WTttd76dkgPvLmQ-9Lk-u8U38KsjYbg8eL2MqqwGDNIrOS79gKi0zyBSpd_s8rWDxiGAvWtFK5YHBBl4teaU9SUEhMsaatBaU_tDhxXqQqTH0dET3ChdOu7eUpAXx5CJZRh7usly0FzNQbihE9pmIgRwCDYRcnjOALnvAwo2K",
                                contentDescription = "Apple Logo",
                                modifier = Modifier.size(20.dp)
                            )
                            Text("Apple", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Terms footer
                Text(
                    text = "Dengan melanjutkan, kamu menyetujui Syarat & Ketentuan kami",
                    fontSize = 12.sp,
                    color = Color(0xFF49454F).copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
