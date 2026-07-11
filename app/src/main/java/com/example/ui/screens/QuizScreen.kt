package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.MainViewModel

@Composable
fun QuizScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val currentIndex by viewModel.currentQuizIndex.collectAsState()
    val selectedChips by viewModel.quizSelectedWordChips.collectAsState()
    val correctState by viewModel.quizCorrectState.collectAsState()
    val isAudioPlaying by viewModel.quizAudioPlaying.collectAsState()
    val score by viewModel.quizScore.collectAsState()

    // Setup steps questions
    val quizTitle = when (currentIndex) {
        0 -> "Susun kalimat: \"Saya makan sushi.\""
        1 -> "Susun kalimat: \"Saya adalah siswa.\""
        else -> "Susun kalimat sesuai percakapan"
    }

    val availableWords = remember(currentIndex) {
        when (currentIndex) {
            0 -> listOf("Watashi", "wa", "Sushi", "o", "tabemasu", "anata", "ringo", "nihon").shuffled()
            1 -> listOf("Watashi", "wa", "Gakusei", "desu", "ka", "sensei", "nihongo").shuffled()
            else -> listOf("Kore", "wa", "nihongo", "no", "hon", "desu", "sore").shuffled()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        // Top Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { viewModel.setScreen(AppScreen.MAIN) }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close quiz",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = "KUIS PERCAKAPAN (${currentIndex + 1}/3)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                letterSpacing = 1.sp
            )

            Text(
                text = "$score XP",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Progress loader bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(100.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth((currentIndex + 1).toFloat() / 3f)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(100.dp))
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Question Title
        Text(
            text = quizTitle,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Waveform Sound Player
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(20.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                // Main audio trigger button
                Button(
                    onClick = { viewModel.playQuizAudio() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.size(52.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Main audio icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Audio waveform visualizer
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "LAFAL PENGUCAPAN",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        letterSpacing = 1.sp
                    )

                    // Waveform visual canvas
                    WaveformVisualizer(isPlaying = isAudioPlaying)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Answer Builder Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(16.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (selectedChips.isEmpty()) {
                    Text(
                        text = "Susun kalimat di sini...",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedChips.forEach { chip ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .clickable(enabled = correctState == null) { viewModel.toggleWordChip(chip) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = chip,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Selection pool choice chips
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 80.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(availableWords) { word ->
                val isSelected = selectedChips.contains(word)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color.Transparent else Color(0xFFE5E7EB),
                            shape = RoundedCornerShape(100.dp)
                        )
                        .clickable(enabled = !isSelected && correctState == null) { viewModel.toggleWordChip(word) }
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = word,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // feedback container & buttons
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AnimatedVisibility(visible = correctState != null) {
                val isCorrect = correctState == true
                val containerBg = if (isCorrect) Color(0xFFE8F5E9) else Color(0xFFFFE0B2)
                val textCol = if (isCorrect) Color(0xFF2E7D32) else Color(0xFFE65100)

                Card(
                    colors = CardDefaults.cardColors(containerColor = containerBg),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = if (isCorrect) "Luar Biasa! Jawaban Benar +20 XP" else "Kurang Tepat!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = textCol
                        )
                        Text(
                            text = when (currentIndex) {
                                0 -> "Jawaban yang benar: \"Watashi wa Sushi o tabemasu\" (Artinya: Saya makan sushi)."
                                1 -> "Jawaban yang benar: \"Watashi wa Gakusei desu\" (Artinya: Saya adalah siswa)."
                                else -> "Jawaban yang benar: \"Kore wa nihongo no hon desu\" (Artinya: Ini adalah buku bahasa Jepang)."
                            },
                            fontSize = 13.sp,
                            color = textCol.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            if (correctState == null) {
                Button(
                    onClick = { viewModel.submitQuizAnswer() },
                    enabled = selectedChips.isNotEmpty(),
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "PERIKSA JAWABAN",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            } else {
                Button(
                    onClick = { viewModel.nextQuizStep() },
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "LANJUTKAN",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRow(
    horizontalArrangement: Arrangement.HorizontalOrVertical,
    verticalArrangement: Arrangement.HorizontalOrVertical,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}

@Composable
fun WaveformVisualizer(isPlaying: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)

    // We animate 15 bar scales
    val animScales = (0..14).map { index ->
        if (isPlaying) {
            infiniteTransition.animateFloat(
                initialValue = 0.2f,
                targetValue = 1.0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 300 + (index * 40) % 200,
                        easing = EaseInOutSine
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "bar_$index"
            )
        } else {
            remember { mutableStateOf(0.15f) }
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
    ) {
        val width = size.width
        val height = size.height
        val barCount = 15
        val gap = 6.dp.toPx()
        val totalGapsWidth = gap * (barCount - 1)
        val barWidth = (width - totalGapsWidth) / barCount

        for (i in 0 until barCount) {
            val scaleValue = animScales[i].value
            val barHeight = height * scaleValue
            val x = i * (barWidth + gap)
            val y = (height - barHeight) / 2

            drawRoundRect(
                color = if (isPlaying) activeColor else inactiveColor,
                topLeft = Offset(x, y),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(100.dp.toPx())
            )
        }
    }
}
