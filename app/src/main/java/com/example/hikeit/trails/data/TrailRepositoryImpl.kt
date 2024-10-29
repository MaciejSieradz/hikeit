package com.example.hikeit.trails.data

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.data.networking.TrailDataSource
import com.example.hikeit.trails.domain.Trail
import com.example.hikeit.trails.domain.TrailDetails
import com.example.hikeit.trails.domain.TrailForm
import com.example.hikeit.trails.domain.TrailRepository
import java.util.UUID

class TrailRepositoryImpl(
    private val trailDataSource: TrailDataSource
) : TrailRepository {

    override suspend fun getTrails(): Result<List<Trail>, NetworkError> {
        return trailDataSource.getTrails()
    }

    override suspend fun getTrailDetails(trailId: String): Result<TrailDetails, NetworkError> {
        return trailDataSource.getTrailDetails(trailId)
    }

    override suspend fun saveTrail(trailForm: TrailForm): Result<Boolean, NetworkError> {
        val trail = TrailDetails(
            id = UUID.randomUUID().toString(),
            trailPhotoUrl = trailForm.photosUri[0].toString(),
            title = trailForm.title,
            difficulty = trailForm.difficulty.difficultyName,
            rating = 0.0,
            numberOfRatings = 0,
            distance = 20.0,
            elevationGain = 2000,
            estimatedHikingTime = trailForm.estimatedHikingTime,
            maxHeight = 2500,
            description = trailForm.description,
            comments = emptyList()
        )

        return trailDataSource.saveTrail(trail)
    }
}
