package com.example.arxdeneme

import android.Manifest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.google.accompanist.permissions.*
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BirdGameEngine(playerName: String, playerAvatar: Int?, modifier: Modifier = Modifier) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var restartTrigger by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            cameraPermissionState.status.isGranted -> {
                CameraPreview(modifier = Modifier.fillMaxSize())

                BirdGameCanvas(
                    restartTrigger = restartTrigger,
                    onGameOver = { restartTrigger++; score = 0 },
                    onScore = { score++ }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    playerAvatar?.let {
                        val avatar: Painter = painterResource(id = it)
                        Image(
                            painter = avatar,
                            contentDescription = "Avatar",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Oyuncu: $playerName",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
                ) {
                    Surface(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = "Skor: $score",
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Bu oyunu oynayabilmek için kamera izni gereklidir.",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                            Text("Kamera İznini Ver")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BirdGameCanvas(
    restartTrigger: Int,
    onGameOver: () -> Unit,
    onScore: () -> Unit
) {
    var birdY by remember { mutableStateOf(600f) }
    val gravity = 3f
    val jump = -35f
    var velocity by remember { mutableStateOf(0f) }

    val screenWidth = 1080f
    val screenHeight = 1920f

    var obstacles by remember { mutableStateOf(mutableListOf<Triple<Float, Float, Int>>()) }
    val obstacleWidth = 100f
    val gapHeight = 300f
    val obstacleSpeed = 6f

    var isGameOver by remember { mutableStateOf(false) }
    var passedObstacles by remember { mutableStateOf(setOf<Int>()) }
    var nextObstacleId by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = restartTrigger) {
        isGameOver = false
        birdY = 600f
        velocity = 0f
        obstacles.clear()
        passedObstacles = setOf()
        nextObstacleId = 0

        while (true) {
            velocity += gravity
            birdY += velocity

            if (birdY < 0f) birdY = 0f

            if (obstacles.isEmpty() || obstacles.last().first < screenWidth - 400f) {
                val safeMargin = 100f
                val maxGapTop = screenHeight - gapHeight - safeMargin
                val gapTop = Random.nextFloat() * (maxGapTop - 400f) + 400f
                obstacles.add(Triple(screenWidth, gapTop, nextObstacleId++))
            }

            obstacles = obstacles.map { Triple(it.first - obstacleSpeed, it.second, it.third) }
                .filter { it.first > -obstacleWidth }
                .toMutableList()

            obstacles.forEach { (x, gapTop, id) ->
                if (x in 160f..240f && birdY !in gapTop..(gapTop + gapHeight)) {
                    isGameOver = true
                }

                if (x < 160f && id !in passedObstacles) {
                    passedObstacles = passedObstacles + id
                    onScore()
                }
            }

            if (birdY > screenHeight) isGameOver = true

            delay(30L)
            if (isGameOver) break
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    if (!isGameOver) velocity = jump
                }
            }
        ) {
            drawCircle(
                color = if (isGameOver) Color.Red else Color.Yellow,
                radius = 50f,
                center = Offset(200f, birdY)
            )

            obstacles.forEach { (x, gapTop, _) ->
                drawRect(
                    color = Color(0xFF007F00),
                    size = Size(obstacleWidth, gapTop),
                    topLeft = Offset(x, 0f)
                )
                drawRect(
                    color = Color(0xFF005F00),
                    size = Size(obstacleWidth, size.height - gapTop - gapHeight),
                    topLeft = Offset(x, gapTop + gapHeight)
                )
            }
        }

        if (isGameOver) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Oyun Bitti!",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onGameOver) {
                    Text("Tekrar Oyna")
                }
            }
        }
    }
}
