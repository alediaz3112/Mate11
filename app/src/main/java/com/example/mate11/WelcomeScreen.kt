package com.example.mate11

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext

@Composable
fun WelcomeScreen(
    onTableSelected: (Int) -> Unit,
    onStoryMode: () -> Unit,
    onChapterSelect: () -> Unit
) {
    val emojis = listOf("🌱", "🦊", "🐱", "🍄", "🌸", "🧚‍♀️", "🐉")
    val titleEmoji = remember { emojis.random() }
    val animeEmojis = remember { List(7) { emojis.random() } }
    val tablas = (2..9).toList()
    val context = LocalContext.current
    val lastTableFlow = remember { TablePrefs.getLastTable(context) }
    val lastTable = lastTableFlow.collectAsState(initial = null).value
    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(300)
        showContent = true
    }
    Scaffold(
        containerColor = Color(0xFFB3E5FC)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(visible = showContent, enter = fadeIn(), exit = fadeOut()) {
                Text(
                    text = "$titleEmoji ¡Bienvenido a Mate11! $titleEmoji",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(visible = showContent, enter = fadeIn(), exit = fadeOut()) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    animeEmojis.forEach { emoji ->
                        Text(
                            text = emoji,
                            fontSize = 28.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (lastTable != null) {
                AnimatedVisibility(visible = showContent, enter = fadeIn(), exit = fadeOut()) {
                    Text(
                        text = "Última tabla seleccionada: $lastTable",
                        fontSize = 18.sp,
                        color = Color(0xFF1976D2),
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            AnimatedVisibility(visible = showContent, enter = fadeIn(), exit = fadeOut()) {
                Text(
                    text = "Elige la tabla para comenzar:",
                    fontSize = 20.sp,
                    color = Color(0xFF388E3C),
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            AnimatedVisibility(visible = showContent, enter = fadeIn(), exit = fadeOut()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    items(tablas) { i ->
                        Button(
                            onClick = {
                                // Guarda la tabla seleccionada antes de navegar
                                CoroutineScope(Dispatchers.IO).launch {
                                    TablePrefs.setLastTable(context, i)
                                }
                                onTableSelected(i)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81D4FA)),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Text(text = "Tabla del $i", fontSize = 18.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            AnimatedVisibility(visible = showContent, enter = fadeIn(), exit = fadeOut()) {
                Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                    Button(
                        onClick = onStoryMode,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(text = "Modo Historia", fontSize = 18.sp)
                    }
                    Button(
                        onClick = onChapterSelect,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(text = "Seleccionar Capítulo", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
