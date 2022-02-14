package com.example.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer.ui.theme.*
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}
private enum class Screen {
    Timer,
    Input
}

private enum class ButtonState {
    Play,
    Pause,
    Restart
}

@Composable
fun MyApp() {
    var currentScreen by rememberSaveable { mutableStateOf(Screen.Timer) }
    Surface(
        color = background1,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (currentScreen == Screen.Input) {
                InputScreen(onInputClick = { currentScreen = Screen.Timer })
            } else TimerScreen(onInputClick = { currentScreen = Screen.Input })
        }

    }
}


@Composable
fun Timer(
    onInputClick: () -> Unit,
    totalTime: Long,
    inactiveBarColor: Color,
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
    val valueAnimation by animateFloatAsState(
        targetValue = value,
        animationSpec = tween(easing = LinearEasing)
    )
    var currentTime by rememberSaveable {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by rememberSaveable {
        mutableStateOf(false)
    }
    // Animation state for button
    var activeButton by rememberSaveable {
        mutableStateOf(ButtonState.Play)
    }
    val buttonTransition = updateTransition(targetState = activeButton, label = "Button Animation")
    val buttonColor by buttonTransition.animateColor(label = "Button Color",
        transitionSpec = { tween(500)}) { state ->
        when (state) {
            ButtonState.Pause -> yellow
            ButtonState.Restart -> green2
            else -> green2
        }
    }
    val buttonCornerDp by buttonTransition.animateDp(label = "Button Shape",
        transitionSpec = { tween(500)}) { state ->
        when (state) {
            ButtonState.Pause -> 20.dp
            else -> 50.dp
        }
    }
    val buttonWidthDp by buttonTransition.animateDp(
        label = "Button Width",
    transitionSpec = { tween(500)}) { state ->
        when (state) {
            ButtonState.Pause -> 140.dp
            else -> 80.dp
        }
    }
    // animate color of arc
    val color = remember { Animatable(green1) }
    var colorChange by rememberSaveable {
        mutableStateOf(false) }
    LaunchedEffect(colorChange) {
        if (currentTime > 0) {
            color.animateTo(
                if (isTimerRunning) Color.Red else color.value,
                animationSpec = tween(
                    durationMillis = 2*totalTime.toInt()
                )
            )
        }
        else color.animateTo(Color.Green, animationSpec = tween(1000))

    }

    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        } else {
            colorChange = false
            activeButton = ButtonState.Restart
        }
    }


    Column {
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
                    color = color.value,
                    startAngle = -90f,
                    sweepAngle = -360f * valueAnimation,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
            ClickableText(
                text = AnnotatedString((currentTime / 1000L).toString()),
                onClick = { onInputClick() },
                style = TextStyle(
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
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
                    colorChange = true

                } else {
                    isTimerRunning = !isTimerRunning
                    colorChange = !colorChange

                }
                activeButton = if (isTimerRunning && currentTime > 0L) ButtonState.Pause
                else ButtonState.Play

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
fun TimerScreen(onInputClick: () -> Unit) {
    Timer(
        onInputClick = onInputClick,
        totalTime = 10L * 1000L,
        inactiveBarColor = Color.DarkGray,
        modifier = Modifier.size(300.dp),
        strokeWidth = 10.dp
    )
}


@Composable
fun InputScreen(onInputClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Input Layout Space", fontWeight = FontWeight.Bold, color = yellow)
        Button(
            modifier = Modifier
                .padding(40.dp),
            onClick = { onInputClick() },
            colors = ButtonDefaults.buttonColors(backgroundColor = orange)
        )
        {
            Text(text = "Back")
        }
    }
}