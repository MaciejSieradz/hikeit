package com.example.hikeit.trails.data.networking

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.domain.Trail
import com.example.hikeit.trails.domain.TrailDetails

interface TrailDataSource {
    suspend fun getTrails() : Result<List<Trail>, NetworkError>

    suspend fun getTrailDetails(id: String) : Result<TrailDetails, NetworkError>

    suspend fun saveTrail(trailDetails: TrailDetails) : Result<Boolean, NetworkError>
}