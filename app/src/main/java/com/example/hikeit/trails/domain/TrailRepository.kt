package com.example.hikeit.trails.domain

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.data.networking.dto.TrailDto

interface TrailRepository {

    suspend fun getTrails(): Result<List<Trail>, NetworkError>

    suspend fun getTrailDetails(trailId: String): Result<TrailDetails, NetworkError>

    suspend fun saveTrail(trailForm: TrailForm): Result<TrailDto, NetworkError>

    suspend fun addReview(trailId: String, review: Review) : Result<Boolean, NetworkError>

    suspend fun markTrailAsSaved(trailId: String) : Result<Boolean, NetworkError>

    suspend fun unmarkTrail(trailId: String) : Result<Boolean, NetworkError>

    suspend fun getSavedTrails() : Result<List<Trail>, NetworkError>

    suspend fun getUserTrails() : Result<List<Trail>, NetworkError>
}