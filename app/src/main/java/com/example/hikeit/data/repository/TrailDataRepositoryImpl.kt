package com.example.hikeit.data.repository

import com.example.hikeit.data.TrailInfo
import com.example.hikeit.data.model.TrailDetails
import com.example.hikeit.data.remote.TrailApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrailDataRepositoryImpl @Inject constructor(
    private val trailApi: TrailApi
) : TrailDataRepository {

    override val trailData: Flow<List<TrailInfo>> = flow {
        emit(trailApi.getAllTrails())
    }

    override suspend fun getTrailDetails(trialId: String): TrailDetails {
        delay(1000)
        return TrailDetails()
    }
}