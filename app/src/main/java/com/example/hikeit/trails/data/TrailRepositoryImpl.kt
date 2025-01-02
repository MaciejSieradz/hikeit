package com.example.hikeit.trails.data

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.data.networking.TrailDataSource
import com.example.hikeit.trails.data.networking.dto.TrailDto
import com.example.hikeit.trails.domain.Review
import com.example.hikeit.trails.domain.Trail
import com.example.hikeit.trails.domain.TrailDetails
import com.example.hikeit.trails.domain.TrailForm
import com.example.hikeit.trails.domain.TrailRepository

class TrailRepositoryImpl(
    private val trailDataSource: TrailDataSource
) : TrailRepository {

    override suspend fun getTrails(): Result<List<Trail>, NetworkError> {
        return trailDataSource.getTrails()
    }

    override suspend fun getTrailDetails(trailId: String): Result<TrailDetails, NetworkError> {
        return trailDataSource.getTrailDetails(trailId)
    }

    override suspend fun saveTrail(trailForm: TrailForm): Result<TrailDto, NetworkError> {
        return trailDataSource.saveTrail(trailForm)
    }

    override suspend fun addReview(trailId: String, review: Review): Result<Boolean, NetworkError> {
        return trailDataSource.addReview(trailId, review)
    }

    override suspend fun markTrailAsSaved(trailId: String): Result<Boolean, NetworkError> {
        return trailDataSource.markTrailAsSaved(trailId)
    }

    override suspend fun unmarkTrail(trailId: String): Result<Boolean, NetworkError> {
        return trailDataSource.unmarkTrail(trailId)
    }

    override suspend fun getSavedTrails(): Result<List<Trail>, NetworkError> {
        return trailDataSource.getSavedTrails()
    }

    override suspend fun getUserTrails(): Result<List<Trail>, NetworkError> {
        return trailDataSource.getUserTrails()
    }

    override suspend fun removeTrail(id: String): Result<Boolean, NetworkError> {
        return trailDataSource.removeTrail(id)
    }

    override suspend fun updateTrail(
        id: String,
        trailForm: TrailForm
    ): Result<TrailDto, NetworkError> {
        return trailDataSource.updateTrail(id, trailForm)
    }
}
