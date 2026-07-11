package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.MainViewModel

@Composable
fun FlashcardScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val activeFlashcards by viewModel.activeFlashcards.collectAsState()
    val currentIndex by viewModel.currentFlashcardIndex.collectAsState()
    val isFlipped by viewModel.isCardFlipped.collectAsState()

    if (activeFlashcards.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val activeWord = activeFlashcards[currentIndex]

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        // Header Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { viewModel.setScreen(AppScreen.MAIN) }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close session",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = "KARTU ${currentIndex + 1} DARI ${activeFlashcards.size}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                letterSpacing = 1.sp
            )

            // Small spacer to balance close button
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Progress bar indicators
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color(0xFFE2E2E2), RoundedCornerShape(100.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth((currentIndex + 1).toFloat() / activeFlashcards.size.toFloat())
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(100.dp))
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Interactive Flashcard Box with Flip animation
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(24.dp))
                .clickable { viewModel.flipCard() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (!isFlipped) {
                        // Front side
                        Text(
                            text = activeWord.kanji,
                            fontSize = 64.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(100.dp))
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = activeWord.reading,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Spacer(modifier = Modifier.height(48.dp))

                        Text(
                            text = "Ketuk untuk melihat arti",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            letterSpacing = 0.5.sp
                        )
                    } else {
                        // Back side (revealed content)
                        Text(
                            text = activeWord.kanji,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = activeWord.reading,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "ARTI BAHASA INDONESIA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = activeWord.translationIndonesian,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Forms / Conjugation text block
                        if (activeWord.formsJson.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                    .padding(12.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "KONJUGASI TATA BAHASA",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = activeWord.formsJson,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Examples sentences
                        if (activeWord.examplesJson.isNotEmpty()) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "CONTOH KALIMAT",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF494552).copy(alpha = 0.6f),
                                    letterSpacing = 1.sp
                                )
                                activeWord.examplesJson.split("\n").forEach { pair ->
                                    val parts = pair.split("_")
                                    if (parts.size == 2) {
                                        Text(
                                            text = parts[0],
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = parts[1],
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Small flip helper icon in top corner
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(100.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isFlipped) "DEPAN" else "BELAKANG",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Session Navigation Controls
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Previous button
            OutlinedButton(
                onClick = { viewModel.prevFlashcard() },
                enabled = currentIndex > 0,
                shape = RoundedCornerShape(100.dp),
                border = BorderStroke(1.dp, Color(0xFFCAC4D4)),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text(
                    text = "Sebelumnya",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (currentIndex > 0) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            }

            // Quick bookmark item
            IconButton(
                onClick = { viewModel.toggleBookmark(activeWord) },
                modifier = Modifier
                    .size(48.dp)
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(100.dp))
            ) {
                Icon(
                    imageVector = if (activeWord.bookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Bookmark flashcard",
                    tint = if (activeWord.bookmarked) MaterialTheme.colorScheme.primary else Color(0xFFCAC4D4)
                )
            }

            // Next button
            val isLast = currentIndex == activeFlashcards.size - 1
            Button(
                onClick = { viewModel.nextFlashcard() },
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text(
                    text = if (isLast) "Selesai" else "Selanjutnya",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
