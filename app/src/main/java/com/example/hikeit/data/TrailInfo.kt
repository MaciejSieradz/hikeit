package com.example.hikeit.data

data class TrailInfo(
    val photo: String,
    val title: String,
    val mountains: String,
    val stars: Double,
    val difficulty: String,
    val distance: Double,
    val hours: Int,
    val minutes: Int,
)

data class LocationDetails(val latitude: Double, val longitude: Double)