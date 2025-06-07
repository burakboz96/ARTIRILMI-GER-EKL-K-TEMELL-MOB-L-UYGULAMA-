package com.example.arxdeneme

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
@Composable
fun GameScreen(
    playerName: String = "Anonim",
    playerAvatar: Int? = null
) {
    var startGame by remember { mutableStateOf(false) }
    var buttonPressed by remember { mutableStateOf(false) }

    // Eğer butona basıldıysa, animasyon sonrası startGame true olacak
    if (buttonPressed) {
        LaunchedEffect(Unit) {
            delay(150)
            startGame = true
            buttonPressed = false // resetleyelim
        }
    }

    if (startGame) {
        BirdGameEngine(
            playerName = playerName,
            playerAvatar = playerAvatar,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        val scale by animateFloatAsState(
            targetValue = if (buttonPressed) 0.95f else 1f,
            animationSpec = tween(durationMillis = 150),
            label = "Button Scale"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1F1C2C), Color(0xFF928DAB))
                    )
                )
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hoşgeldin,\n$playerName!",
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            if (playerAvatar != null) {
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .shadow(12.dp, CircleShape)
                        .background(Color.White.copy(0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = playerAvatar),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(110.dp)
                            .background(Color.DarkGray, CircleShape)
                            .padding(8.dp)
                    )
                }
            } else {
                Text(
                    text = "Avatar seçilmedi",
                    color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    buttonPressed = true
                },
                modifier = Modifier
                    .scale(scale)
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C9A7))
            ) {
                Text(
                    text = "Oyuna Başla",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}