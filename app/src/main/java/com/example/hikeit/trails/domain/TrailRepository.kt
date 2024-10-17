package com.example.hikeit.trails.domain

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.data.model.TrailDetails

interface TrailRepository {

    suspend fun getTrails() : Result<List<Trail>, NetworkError>

    suspend fun getTrailDetails(trailId: String) : Result<TrailDetails, NetworkError>
}