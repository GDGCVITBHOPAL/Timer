package com.example.timer.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputScreen(onInputClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Selection(Modifier, "00", "h")
            Selection(Modifier, "00", "m")
            Selection(Modifier, "00", "s")
        }


        Spacer(modifier = Modifier.padding(50.dp))
        Row(){
            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .size(80.dp)
                    ,
                onClick = { onInputClick() },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = orange
                )
            )
            {
                Icon(imageVector = Icons.Filled.Close,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp))
            }
            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .size(80.dp),
                onClick = { onInputClick() },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = green2
                )
            )
            {
                Icon(imageVector = Icons.Filled.Done,
                    contentDescription = "Done",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp))
            }
        }
    }
}

@Composable
fun Selection(modifier: Modifier,digit: String, unit: String){
    Box (Modifier.padding(4.dp)){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier.padding(0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(85.dp) ) {
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
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(85.dp) )  {
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

@Preview
@Composable
fun SelectionPreview(){
    Selection(Modifier, "00", "h")
}

@Preview(widthDp = 320)
@Composable
fun InputScreenPreview(){
    InputScreen(onInputClick = { })
}