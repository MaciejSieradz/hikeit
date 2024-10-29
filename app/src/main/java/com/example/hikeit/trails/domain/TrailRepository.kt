package com.example.hikeit.trails.domain

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result

interface TrailRepository {

    suspend fun getTrails(): Result<List<Trail>, NetworkError>

    suspend fun getTrailDetails(trailId: String): Result<TrailDetails, NetworkError>

    suspend fun saveTrail(trailForm: TrailForm): Result<Boolean, NetworkError>
}