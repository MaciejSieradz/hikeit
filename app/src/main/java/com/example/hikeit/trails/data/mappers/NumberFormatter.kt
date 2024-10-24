package com.example.hikeit.trails.data.mappers

import java.text.NumberFormat
import java.util.Locale

fun getPolishNumberFormatter(maxFractionDigits: Int = 1, minFractionDigits: Int = 1): NumberFormat =
    NumberFormat.getNumberInstance(Locale("pl")).apply {
        maximumFractionDigits = maxFractionDigits
        minimumFractionDigits = minFractionDigits
    }