package com.example.hikeit.data.repository

import com.example.hikeit.data.TrailInfo
import kotlinx.coroutines.flow.Flow

interface TrailDataRepository {

    val trailData: Flow<List<TrailInfo>>

}