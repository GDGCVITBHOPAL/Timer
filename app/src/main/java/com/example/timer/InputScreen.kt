package com.example.timer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer.ui.theme.green2
import com.example.timer.ui.theme.orange
import com.example.timer.ui.theme.orangeLight

@Composable
fun InputScreen(onInputClick: (inputTime: Long) -> Unit, currentTime: Long) {
    var seconds by remember {
        mutableStateOf(((currentTime%3600)%60))
    }
    var minutes by remember {
        mutableStateOf((currentTime%3600)/60)
    }
    var hours by remember {
        mutableStateOf(currentTime/3600)
    }
    var newTime = (hours*3600+minutes*60+seconds)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            // Hours
            Selection(modifier = Modifier,
                digit = addZero(hours),
                unit = "h",
                onDecClick = { hours++ },
                onIncClick = { if (hours > 0L) hours-- })

            // Minutes
            Selection(modifier = Modifier,
                digit = addZero(minutes),
                unit = "m",
                onDecClick = {
                    if (minutes <= 59L) {
                        if (minutes == 59L) {
                            minutes = 0L
                        } else
                            minutes++
                    }
                },
                onIncClick = {
                    if (minutes >= 0L) {
                        if (minutes == 0L) {
                            minutes = 59L
                        } else
                            minutes--
                    }
                }
            )

            // Seconds
            Selection(modifier = Modifier,
                digit = addZero(seconds),
                unit = "s",
                onDecClick = {
                    if (seconds <= 59L) {
                        if (seconds == 59L) {
                            seconds = 0L
                        } else
                            seconds++
                    }
                },
                onIncClick = {
                    if (seconds >= 0L) {
                        if (seconds == 0L) {
                            seconds = 59L
                        } else
                            seconds--
                    }
                }
            )
        }


        Spacer(modifier = Modifier.padding(50.dp))
        Row() {
            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .size(80.dp),
                onClick = { onInputClick(currentTime) },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = orange
                )
            )
            {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .size(80.dp),
                onClick = { onInputClick(newTime) },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = green2
                )
            )
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun Selection(
    onIncClick: () -> Unit,
    onDecClick: () -> Unit,
    modifier: Modifier,
    digit: String,
    unit: String
) {
    Box(Modifier.padding(4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier.padding(0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { onIncClick() },
                    modifier = Modifier.size(85.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ExpandLess,
                        contentDescription = "Increase",
                        modifier = Modifier.size(80.dp),
                        tint = orangeLight
                    )
                }
                Text(
                    digit,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 44.sp
                )
                IconButton(
                    onClick = { onDecClick() },
                    modifier = Modifier.size(85.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ExpandMore,
                        contentDescription = "Decrease",
                        modifier = Modifier.size(80.dp),
                        tint = orangeLight
                    )
                }
            }
            Text(
                text = unit,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

