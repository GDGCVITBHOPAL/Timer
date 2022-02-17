package com.example.timer

fun addZero(digit: Long): String{
    return if (digit in 0..9) {
        "0$digit"
    } else {
        "$digit"
    }
}

fun inTimeFormat(currentTime: Long): String{
    val time = currentTime / 1000L
    var seconds = (time%3600)%60
    var minutes = (time%3600)/60
    var hours = time/3600
    var timeInFormat = ""

    if (hours > 0)
        timeInFormat = "${addZero(hours)} : ${addZero(minutes)} : ${addZero(seconds)}"
    else if (minutes > 0)
        timeInFormat = "${addZero(minutes)} : ${addZero(seconds)}"
    else timeInFormat = "$seconds"

    return timeInFormat
}

