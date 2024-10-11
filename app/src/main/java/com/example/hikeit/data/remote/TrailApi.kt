package com.example.hikeit.data.remote

import com.example.hikeit.data.TrailInfo
import kotlinx.coroutines.flow.Flow

interface TrailApi {

    fun getAllTrails() : Flow<List<TrailInfo>>
}