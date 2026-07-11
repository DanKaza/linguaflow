package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainViewModel
import com.example.ui.theme.SoftLavenderBg

@Composable
fun PeringkatTab(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val userName by viewModel.userName.collectAsState()
    val leaderboard = viewModel.leaderboard

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
    ) {
        // Header Banner card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Leaderboard,
                            contentDescription = "Leaderboard Logo",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Peringkat Liga Kanji",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Dapatkan XP dari kuis & latihan harian",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // Leaderboard list rows
        itemsIndexed(leaderboard) { index, pair ->
            val isCurrentUser = pair.first.contains("Kamu")
            val name = if (isCurrentUser) userName else pair.first

            val rankNum = index + 1
            val rankColor = when (rankNum) {
                1 -> Color(0xFFF1C40F) // Gold
                2 -> Color(0xFF7A7583) // Silver (High Density grey purple style)
                3 -> Color(0xFFE67E22) // Bronze
                else -> Color(0xFF7A7583)
            }

            val cardBg = if (isCurrentUser) SoftLavenderBg else Color.White
            val cardBorder = if (isCurrentUser) MaterialTheme.colorScheme.primary.copy(alpha = 0.6f) else Color(0xFFCAC4D0)

            Card(
                colors = CardDefaults.cardColors(containerColor = cardBg),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, cardBorder, RoundedCornerShape(16.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Rank number / crown badge
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(rankColor.copy(alpha = 0.1f), RoundedCornerShape(100.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "#$rankNum",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = rankColor
                            )
                        }

                        // Learner name
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = name,
                                fontSize = 16.sp,
                                fontWeight = if (isCurrentUser) FontWeight.Bold else FontWeight.Normal,
                                color = if (isCurrentUser) MaterialTheme.colorScheme.primary else Color(0xFF1D1B20)
                            )
                            Text(
                                text = "Liga Pemula",
                                fontSize = 12.sp,
                                color = Color(0xFF49454F).copy(alpha = 0.7f)
                            )
                        }
                    }

                    // Score with Star icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "XP star",
                            tint = Color(0xFFF1C40F),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "${pair.second} XP",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1D1B20)
                        )
                    }
                }
            }
        }
    }
}
