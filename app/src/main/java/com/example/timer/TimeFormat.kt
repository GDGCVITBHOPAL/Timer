package com.example.timer

fun addZero(digit: Long): String{
    return if (digit in 0..9) {
        "0$digit"
    } else {
        "$digit"
    }
}

