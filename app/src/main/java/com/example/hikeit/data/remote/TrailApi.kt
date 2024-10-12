package com.example.hikeit.data.remote

import com.example.hikeit.data.TrailInfo

interface TrailApi {

    suspend fun getAllTrails() : List<TrailInfo>
}