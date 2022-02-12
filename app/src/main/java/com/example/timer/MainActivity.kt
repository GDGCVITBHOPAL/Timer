package com.example.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer.ui.theme.background1
import com.example.timer.ui.theme.green
import com.example.timer.ui.theme.ring1
import com.example.timer.ui.theme.yellow
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main_Content()
        }
    }
}

private enum class ButtonState(){
    Play,
    Pause,
    Restart
}

@Composable
fun Timer(
    totalTime: Long,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    initialValue: Float = 1f,
    strokeWidth: Dp = 8.dp
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by rememberSaveable {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
    }

    // Animation state for button
    var activeButton by rememberSaveable {
        mutableStateOf(ButtonState.Play)
    }
    val buttonTransition = updateTransition(targetState = activeButton, label = "Button Animation")
    val buttonColor by buttonTransition.animateColor(label = "Button Color") { state ->
        when(state){
            ButtonState.Pause -> yellow
            else -> green
        }
    }
    val buttonCornerDp by buttonTransition.animateDp(label = "Button Shape") { state ->
        when(state){
            ButtonState.Pause -> 20.dp
            else -> 50.dp
        }
    }
    val buttonWidthDp by buttonTransition.animateDp(label = "Button Width") { state ->
        when(state){
            ButtonState.Pause -> 140.dp
            else -> 80.dp
        }
    }
    Column() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .onSizeChanged {
                    size = it
                }
        ) {
            Canvas(modifier = modifier) {
                drawArc(
                    color = inactiveBarColor,
                    startAngle = 360f,
                    sweepAngle = 360f,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = activeBarColor,
                    startAngle = 360f,
                    sweepAngle = 360f * value,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
            Text(
                text = (currentTime / 1000L).toString(),
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(140.dp))
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(height = 80.dp, width = buttonWidthDp),
            onClick = {
                if (currentTime <= 0L) {
                    currentTime = totalTime
                    isTimerRunning = true
                } else {
                    isTimerRunning = !isTimerRunning
                }
                activeButton = if (isTimerRunning && currentTime > 0L) ButtonState.Pause
                else if (!isTimerRunning && currentTime >= 0L) ButtonState.Play
                else ButtonState.Restart
            },
            contentPadding = PaddingValues(0.dp),

            shape = RoundedCornerShape(buttonCornerDp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor
            )
        ) {
            Icon(
                tint = background1,
                modifier = Modifier.size(50.dp),
                contentDescription = if (isTimerRunning && currentTime > 0L) "Pause"
                else if (!isTimerRunning && currentTime >= 0L) "Play"
                else "Restart",
                imageVector = if (isTimerRunning && currentTime > 0L) Icons.Filled.Pause
                else if (!isTimerRunning && currentTime >= 0L) Icons.Filled.PlayArrow
                else Icons.Filled.Refresh
            )
        }

    }

}

@Composable
fun Main_Content() {
    Surface(
        color = background1,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Timer(
                totalTime = 100L * 1000L,
                inactiveBarColor = Color.DarkGray,
                activeBarColor = ring1,
                modifier = Modifier.size(300.dp),
                strokeWidth = 10.dp
            )
        }
    }
}

@Preview("Main Screen")
@Composable
fun preview_Timer() {
    Main_Content()
}