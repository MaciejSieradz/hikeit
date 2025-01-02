package com.example.hikeit.trails.data.networking

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.data.networking.dto.TrailDto
import com.example.hikeit.trails.domain.Review
import com.example.hikeit.trails.domain.Trail
import com.example.hikeit.trails.domain.TrailDetails
import com.example.hikeit.trails.domain.TrailForm

interface TrailDataSource {
    suspend fun getTrails() : Result<List<Trail>, NetworkError>

    suspend fun getTrailDetails(id: String) : Result<TrailDetails, NetworkError>

    suspend fun saveTrail(trailForm: TrailForm) : Result<TrailDto, NetworkError>

    suspend fun addReview(trailId: String, review: Review) : Result<Boolean, NetworkError>

    suspend fun markTrailAsSaved(trailId: String) : Result<Boolean, NetworkError>

    suspend fun unmarkTrail(trailId: String) : Result<Boolean, NetworkError>

    suspend fun getSavedTrails() : Result<List<Trail>, NetworkError>

    suspend fun getUserTrails() : Result<List<Trail>, NetworkError>
}