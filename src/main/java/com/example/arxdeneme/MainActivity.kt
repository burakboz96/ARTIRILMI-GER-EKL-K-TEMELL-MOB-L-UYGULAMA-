package com.example.arxdeneme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.arxdeneme.ui.theme.ArxDenemeTheme
import androidx.compose.ui.draw.clip

import androidx.compose.runtime.Composable

import androidx.compose.ui.draw.shadow




import androidx.compose.ui.unit.sp

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text


import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.sp
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArxDenemeTheme {
                var screenState by remember { mutableStateOf("welcome") }
                var playerName by remember { mutableStateOf("") }
                var playerAvatar by remember { mutableStateOf<Int?>(null) }

                when (screenState) {
                    "welcome" -> WelcomeScreen(onStartClicked = { screenState = "avatar" })
                    "avatar" -> AvatarScreen(
                        onFinish = { name, avatar ->
                            playerName = name
                            playerAvatar = avatar
                            screenState = "game"
                        }
                    )
                    "game" -> GameScreen(playerName, playerAvatar)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(onStartClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D324D), Color(0xFF7F5A83))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(32.dp)
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(24.dp))
                .background(Color(0xFF1E1E2C), shape = RoundedCornerShape(24.dp))
                .padding(vertical = 40.dp, horizontal = 32.dp)
        ) {
            Text(
                text = "ARX Oyununa\nHoş geldiniz",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Box(
                modifier = Modifier
                    .shadow(8.dp, RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF56AB2F), Color(0xFFA8E063))
                        )
                    )
                    .height(55.dp)
                    .width(220.dp)
            ) {
                Button(
                    onClick = onStartClicked,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues()
                ) {
                    Text(
                        text = "Başla",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

// Extension function to convert Brush to Color (approximation)
