package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.ChatMessage
import com.example.ui.MainViewModel
import com.example.ui.theme.SoftLavenderBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiSenseiView(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val isExpanded by viewModel.aiSenseiExpanded.collectAsState()
    val chatMessages by viewModel.aiChatMessages.collectAsState()
    val isThinking by viewModel.isAiThinking.collectAsState()

    var inputQuery by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()

    // Automatically scroll to bottom when new messages arrive
    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            lazyListState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Floating Action Button to toggle AI Sensei
        if (!isExpanded) {
            Button(
                onClick = { viewModel.toggleAiSensei(true) },
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 90.dp, end = 20.dp)
                    .height(52.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Sensei star",
                        tint = Color.White
                    )
                    Text(
                        text = "Tanya Sensei",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // Expandable Chat Sheet
        AnimatedVisibility(
            visible = isExpanded,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.78f)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFCAC4D0),
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Chat Sheet Header
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SoftLavenderBg)
                            .padding(horizontal = 20.dp, vertical = 14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "Sensei logo",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = "AI Sensei",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Berpikir Mendalam (gemini-3.1-pro)",
                                    fontSize = 11.sp,
                                    color = Color(0xFF49454F)
                                )
                            }
                        }

                        IconButton(
                            onClick = { viewModel.toggleAiSensei(false) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Minimize sensei",
                                tint = Color(0xFF49454F)
                            )
                        }
                    }

                    // Chat messages list
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
                    ) {
                        items(chatMessages) { message ->
                            ChatBubble(message)
                        }

                        // Thinking indicator bubble
                        if (isThinking) {
                            item {
                                ThinkingBubble()
                            }
                        }
                    }

                    // Suggestion Prompt Chips
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val prompts = listOf(
                            "Jelaskan partikel は dan が",
                            "Contoh kalimat kata kerja 食べる",
                            "Apa beda Kudasai dan Onegai?",
                            "Cara menyapa di pagi hari"
                        )

                        items(prompts) { prompt ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(100.dp))
                                    .background(SoftLavenderBg)
                                    .border(1.dp, Color(0xFFCAC4D0), RoundedCornerShape(100.dp))
                                    .clickable(enabled = !isThinking) {
                                        viewModel.sendQuestionToSensei(prompt)
                                    }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = prompt,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    // Input Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 1.dp, color = Color(0xFFCAC4D0))
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        OutlinedTextField(
                            value = inputQuery,
                            onValueChange = { inputQuery = it },
                            placeholder = { Text("Tanya tata bahasa Jepang...") },
                            singleLine = true,
                            shape = RoundedCornerShape(100.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color(0xFFCAC4D0),
                                focusedContainerColor = Color(0xFFF9F9F9),
                                unfocusedContainerColor = Color(0xFFF9F9F9)
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = {
                                if (inputQuery.trim().isNotEmpty()) {
                                    viewModel.sendQuestionToSensei(inputQuery)
                                    inputQuery = ""
                                }
                            },
                            enabled = inputQuery.trim().isNotEmpty() && !isThinking,
                            shape = RoundedCornerShape(100.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send prompt",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleColor = if (message.isUser) SoftLavenderBg else MaterialTheme.colorScheme.surfaceVariant
    val align = if (message.isUser) Alignment.End else Alignment.Start

    Column(
        horizontalAlignment = align,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = bubbleColor,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isUser) 16.dp else 4.dp,
                        bottomEnd = if (message.isUser) 4.dp else 16.dp
                    )
                )
                .padding(14.dp)
        ) {
            Text(
                text = message.text,
                fontSize = 14.sp,
                color = Color(0xFF1D1B20),
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun ThinkingBubble() {
    val infiniteTransition = rememberInfiniteTransition(label = "think")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotate"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .rotate(rotation)
                .background(SoftLavenderBg, RoundedCornerShape(100.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Thinking spinner",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
        }

        Text(
            text = "AI Sensei sedang berpikir mendalam... 🧠✨",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
