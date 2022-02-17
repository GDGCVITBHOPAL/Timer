package com.example.timer

import Timer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.timer.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}




@Composable
fun MyApp() {
    var currentScreen by rememberSaveable { mutableStateOf(Screen.Timer) }
    var time = 10L
    Surface(
        color = background1,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (currentScreen == Screen.Input) {
                InputScreen(onInputClick = {
                                            currentScreen = Screen.Timer
                                            time = it}, time)
            } else TimerScreen(onInputClick = { currentScreen = Screen.Input }, time = time)
        }

    }
}


@Composable
fun TimerScreen(onInputClick: () -> Unit, time: Long) {
    Timer(
        onInputClick = onInputClick,
        totalTime = time * 1000L,
        inactiveBarColor = Color.DarkGray,
        modifier = Modifier.size(300.dp),
        strokeWidth = 10.dp
    )
}


