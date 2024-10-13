package com.example.hikeit.data.repository

import com.example.hikeit.data.TrailInfo
import com.example.hikeit.data.model.TrailDetails
import kotlinx.coroutines.flow.Flow

interface TrailDataRepository {

    val trailData: Flow<List<TrailInfo>>

    suspend fun getTrailDetails(trialId: String) : TrailDetails
}