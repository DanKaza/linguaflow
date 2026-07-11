package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BrandPrimary = Color(0xFF6750A4)
val BrandSecondary = Color(0xFF625B71)
val BrandPrimaryContainer = Color(0xFFEADDFF)
val BrandOnPrimaryContainer = Color(0xFF21005D)
val BrandSecondaryContainer = Color(0xFFE8DEF8)
val BrandOnSecondaryContainer = Color(0xFF1D192B)
val BrandBackground = Color(0xFFFEF7FF)
val BrandSurface = Color(0xFFFFFFFF)

val SoftLavenderBg: Color
    @Composable
    get() = if (MaterialTheme.colorScheme.background == Color(0xFF121212)) Color(0xFF25232A) else Color(0xFFF3EDF7)

val OutlineVariant = Color(0xFFCAC4D0)
val ErrorContainer = Color(0xFFF9DEDC)
val OnErrorContainer = Color(0xFF410E0B)
val SuccessBg = Color(0xFFD1E1FF)
val SuccessText = Color(0xFF001D35)
val PinkAccentBg = Color(0xFFFFD8E4)
val PinkAccentText = Color(0xFF31111D)

val BlueAccentBg: Color
    @Composable
    get() = if (MaterialTheme.colorScheme.background == Color(0xFF121212)) Color(0xFF004A77) else Color(0xFFD1E1FF)

val BlueAccentText: Color
    @Composable
    get() = if (MaterialTheme.colorScheme.background == Color(0xFF121212)) Color(0xFFC2E8FF) else Color(0xFF001D35)

val GrayAccentBg: Color
    @Composable
    get() = if (MaterialTheme.colorScheme.background == Color(0xFF121212)) Color(0xFF313033) else Color(0xFFE6E1E5)

val GrayAccentText: Color
    @Composable
    get() = if (MaterialTheme.colorScheme.background == Color(0xFF121212)) Color(0xFFE6E1E5) else Color(0xFF49454F)

// Speech practice / Zenith Lingua accent colors
val GreenAccent = Color(0xFF10B981)
val YellowAccent = Color(0xFFF59E0B)
val RedAccent = Color(0xFFEF4444)
val Emerald50 = Color(0xFFECFDF5)
val Amber50 = Color(0xFFFFFBEB)
val Red50 = Color(0xFFFEF2F2)

// Material tonal surface colors
val SurfaceContainerLowest = Color(0xFFFFFFFF)
val SurfaceContainerLow = Color(0xFFF7F2FA)
val SurfaceContainer = Color(0xFFF3EDF7)
val SurfaceContainerHigh = Color(0xFFECE6F0)
val SurfaceContainerHighest = Color(0xFFE6E1E9)
