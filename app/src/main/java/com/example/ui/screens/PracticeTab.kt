package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PracticeHistory
import com.example.ui.MainViewModel
import com.example.ui.theme.SoftLavenderBg
import com.example.ui.theme.BlueAccentBg
import com.example.ui.theme.BlueAccentText
import com.example.ui.theme.GrayAccentBg
import com.example.ui.theme.GrayAccentText
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PracticeTab(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val histories by viewModel.histories.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
    ) {
        // Daily practice banner card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SoftLavenderBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFCAC4D0), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Trophy",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = "Latihan Harian",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "2 dari 3 selesai",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            // Small progress bar
                            Box(
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(5.dp)
                                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), RoundedCornerShape(100.dp))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(0.66f)
                                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(100.dp))
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { viewModel.startQuizSession() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(100.dp),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Text(
                            text = "Mulai",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Kategori Latihan grid
        item {
            Column {
                Text(
                    text = "KATEGORI LATIHAN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF494552).copy(alpha = 0.6f),
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Custom grid items
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        GridItem(
                            title = "Kuis Kosakata",
                            icon = Icons.Default.Book,
                            containerColor = Color(0xFFEDE9FE),
                            iconColor = Color(0xFF674BB5),
                            modifier = Modifier.weight(1f)
                        ) {
                            viewModel.startQuizSession()
                        }

                        GridItem(
                            title = "Kuis Kanji",
                            icon = Icons.Default.Brush,
                            containerColor = Color(0xFFFDF2E9),
                            iconColor = Color(0xFFE67E22),
                            modifier = Modifier.weight(1f)
                        ) {
                            viewModel.startQuizSession()
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        GridItem(
                            title = "Tata Bahasa",
                            icon = Icons.Default.HistoryEdu,
                            containerColor = Color(0xFFE6F4EA),
                            iconColor = Color(0xFF137333),
                            modifier = Modifier.weight(1f)
                        ) {
                            viewModel.startQuizSession()
                        }

                        GridItem(
                            title = "Latihan Ucapan",
                            icon = Icons.Default.Mic,
                            containerColor = Color(0xFFFCE8E6),
                            iconColor = Color(0xFFC5221F),
                            modifier = Modifier.weight(1f)
                        ) {
                            viewModel.startQuizSession()
                        }
                    }
                }
            }
        }

        // Riwayat Latihan history list
        item {
            Text(
                text = "RIWAYAT LATIHAN",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF494552).copy(alpha = 0.6f),
                letterSpacing = 1.sp
            )
        }

        if (histories.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada riwayat latihan.",
                        fontSize = 14.sp,
                        color = Color(0xFF494552).copy(alpha = 0.6f)
                    )
                }
            }
        } else {
            items(histories) { item ->
                PracticeHistoryRow(item)
            }
        }
    }
}

@Composable
fun GridItem(
    title: String,
    icon: ImageVector,
    containerColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .border(1.dp, Color(0xFFCAC4D0), RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(containerColor, RoundedCornerShape(100.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun PracticeHistoryRow(history: PracticeHistory) {
    val dateString = remember(history.timestamp) {
        val sdf = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
        sdf.format(Date(history.timestamp))
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFCAC4D0), RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = "History",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = history.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = dateString,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Score badge
            val badgeBg = if (history.score >= 80) BlueAccentBg else GrayAccentBg
            val badgeText = if (history.score >= 80) BlueAccentText else GrayAccentText

            Box(
                modifier = Modifier
                    .background(badgeBg, RoundedCornerShape(100.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${history.score}%",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = badgeText
                )
            }
        }
    }
}
