package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.Word
import com.example.ui.MainViewModel
import com.example.ui.theme.SoftLavenderBg
import com.example.ui.theme.BlueAccentBg
import com.example.ui.theme.BlueAccentText

@Composable
fun WordDetailSheet(
    viewModel: MainViewModel
) {
    val word by viewModel.selectedWordForDetail.collectAsState()

    if (word == null) return

    val activeWord = word!!

    Dialog(
        onDismissRequest = { viewModel.selectWordForDetail(null) }
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
                .border(1.dp, Color(0xFFCAC4D0), RoundedCornerShape(24.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Top Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(6.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = activeWord.jlptLevel,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    IconButton(
                        onClick = { viewModel.selectWordForDetail(null) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close details",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                // Word Identity
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = activeWord.kanji,
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = activeWord.reading,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = activeWord.translationIndonesian,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = activeWord.translationEnglish,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }

                HorizontalDivider(color = Color(0xFFCAC4D0), thickness = 0.5.dp)

                // Grammatical Forms / Conjugation list
                if (activeWord.formsJson.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "KONJUGASI TATA BAHASA",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF49454F).copy(alpha = 0.6f),
                            letterSpacing = 1.sp
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SoftLavenderBg, RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = activeWord.formsJson,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                // Example sentences
                if (activeWord.examplesJson.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "CONTOH KALIMAT",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF49454F).copy(alpha = 0.6f),
                            letterSpacing = 1.sp
                        )

                        activeWord.examplesJson.split("\n").forEach { sentencePair ->
                            val parts = sentencePair.split("_")
                            if (parts.size == 2) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, Color(0xFFCAC4D0), RoundedCornerShape(12.dp))
                                        .padding(12.dp)
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(
                                            text = parts[0],
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = parts[1],
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(color = Color(0xFFCAC4D0), thickness = 0.5.dp)

                // Bottom action buttons (Bookmark, Mark as mastered)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Bookmark toggle button
                    OutlinedButton(
                        onClick = { viewModel.toggleBookmark(activeWord) },
                        shape = RoundedCornerShape(100.dp),
                        border = BorderStroke(1.dp, Color(0xFFCAC4D0)),
                        modifier = Modifier
                            .weight(1.2f)
                            .height(48.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = if (activeWord.bookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (activeWord.bookmarked) MaterialTheme.colorScheme.primary else Color(0xFFCAC4D0)
                            )
                            Text(
                                text = if (activeWord.bookmarked) "Ditandai" else "Tandai",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Learned master toggle button
                    Button(
                        onClick = {
                            viewModel.markAsLearned(activeWord, !activeWord.learned)
                            viewModel.selectWordForDetail(null)
                        },
                        shape = RoundedCornerShape(100.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (activeWord.learned) BlueAccentBg else MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(48.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = if (activeWord.learned) Icons.Default.CheckCircle else Icons.Default.CheckCircleOutline,
                                contentDescription = "Mastered",
                                tint = if (activeWord.learned) BlueAccentText else Color.White
                            )
                            Text(
                                text = if (activeWord.learned) "Dikuasai" else "Kuasai Kata",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (activeWord.learned) BlueAccentText else Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
