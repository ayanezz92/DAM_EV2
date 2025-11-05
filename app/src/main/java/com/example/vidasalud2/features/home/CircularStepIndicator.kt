package com.example.vidasalud2.features.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularStepIndicator(
    currentValue: Int,
    goalValue: Int,
    indicatorColor: Color = Color(0xFF6FAF4E), // Verde principal
    trackColor: Color = Color(0xFFDCEBDB), // Verde claro
    strokeWidth: Dp = 20.dp
) {
    val progress = (currentValue.toFloat() / goalValue.toFloat())

    // Estado para la animación
    var animationPlayed by remember { mutableStateOf(false) }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) progress else 0f,
        animationSpec = tween(durationMillis = 1000), // 1 segundo de animación
        label = "StepProgressAnimation"
    )

    // Lanza la animación solo la primera vez que se compone
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(250.dp) // Tamaño del círculo
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val size = Size(width = size.width, height = size.height)

            // 1. Dibujar el círculo de fondo (track)
            drawArc(
                color = trackColor,
                startAngle = -90f, // Empezar desde arriba
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            // 2. Dibujar el círculo de progreso (animado)
            drawArc(
                color = indicatorColor,
                startAngle = -90f,
                sweepAngle = 360f * currentPercentage.value,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        // 3. Textos interiores
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$currentValue",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Pasos",
                fontSize = 18.sp,
                color = Color.Gray
            )
            Text(
                text = "Meta: $goalValue",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}