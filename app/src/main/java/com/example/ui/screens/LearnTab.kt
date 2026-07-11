package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Flag
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
import com.example.ui.MainViewModel

@Composable
fun LearnTab(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("Semua") }
    val filters = listOf("Semua", "Kosakata", "Kanji", "Tata Bahasa")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
    ) {
        // Filter Chips row
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                filters.forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.secondaryContainer 
                                else Color.Transparent
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) Color.Transparent else Color(0xFFCAC4D0),
                                shape = RoundedCornerShape(100.dp)
                             )
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = filter,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else Color(0xFF49454F)
                        )
                    }
                }
            }
        }

        // Lanjutkan Belajar Card
        item {
            Column {
                Text(
                    text = "LANJUTKAN BELAJAR",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF494552).copy(alpha = 0.6f),
                    letterSpacing = 1.sp
                )
                
                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "SESI BELAJAR",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    letterSpacing = 1.2.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Jepang N5",
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "Kosakata Dasar",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                )
                            }

                            Button(
                                onClick = { viewModel.startFlashcardSession("Semua") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(100.dp),
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                                modifier = Modifier.height(40.dp)
                            ) {
                                Text(
                                    text = "Lanjut",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Progress Belajar",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                )
                                Text(
                                    text = "60%",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f), RoundedCornerShape(100.dp))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(0.60f)
                                        .background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(100.dp))
                                )
                            }
                        }
                    }
                }
            }
        }

        // Pilih Level horizontal scroll
        item {
            Column {
                Text(
                    text = "PILIH LEVEL",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF494552).copy(alpha = 0.6f),
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val levels = listOf(
                        Triple("LEVEL", "五", "N5\n800 Kata"),
                        Triple("LEVEL", "四", "N4\n1.5k Kata"),
                        Triple("LEVEL", "三", "N3\n3k Kata")
                    )

                    levels.forEach { level ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp, Color(0xFFCAC4D0), RoundedCornerShape(16.dp))
                                .clickable { viewModel.startFlashcardSession("Semua") },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = level.first,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF49454F).copy(alpha = 0.6f)
                                )
                                Text(
                                    text = level.second,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (level.second == "五") MaterialTheme.colorScheme.primary else Color(0xFFCAC4D0)
                                )
                                Text(
                                    text = level.third,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        // Deck Flashcards section
        item {
            Column {
                Text(
                    text = "DECK FLASHCARD",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF494552).copy(alpha = 0.6f),
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    val decks = listOf(
                        Triple("Kata Benda Utama", "Kata Benda", "50 Kartu • Terakhir: 2 Jan"),
                        Triple("Kata Kerja N5", "Kata Kerja", "120 Kartu • Terakhir: 1 Jan"),
                        Triple("Sapaan Harian", "Frasa", "30 Kartu • Terakhir: 28 Des")
                    )

                    decks.forEach { deck ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color(0xFFCAC4D0), RoundedCornerShape(16.dp))
                                .clickable { viewModel.startFlashcardSession(deck.second) },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = deck.first,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )

                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                                    shape = RoundedCornerShape(6.dp)
                                                )
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = deck.second.uppercase(),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        }
                                    }

                                    Text(
                                        text = deck.third,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = "Chevron",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
