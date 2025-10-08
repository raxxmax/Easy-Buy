package com.example.easybuy.Splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Animation states
    var startAnimation by remember { mutableStateOf(false) }

    // Scale animation for logo
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    // Alpha animation for text
    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 500
        ),
        label = "alpha"
    )

    // Infinite pulse animation for icon background
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Start animations and navigate after delay
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2100) // Show splash for 2.1 seconds
        navController.navigate("signin") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E2F8E),
                        Color(0xFF60CFDC),
                        Color(0xFFEEB36A)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated logo with pulsing background
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .scale(scale)
                    .scale(if (startAnimation) pulseScale else 1f)
                    .background(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "App Logo",
                    modifier = Modifier.size(70.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // App name with fade-in animation
            Text(
                text = "EasyBuy",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = alpha),
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline with fade-in animation
            Text(
                text = "Find the Best Deals",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = Color.White.copy(alpha = alpha * 0.9f),
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Linear progress indicator
            LinearProgressIndicator(
                modifier = Modifier
                    .width(200.dp)
                    .height(4.dp),
                color = Color.White.copy(alpha = alpha),
                trackColor = Color.White.copy(alpha = alpha * 0.3f)
            )
        }
    }
}

