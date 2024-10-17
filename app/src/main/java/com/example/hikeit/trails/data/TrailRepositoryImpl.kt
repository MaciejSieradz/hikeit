package com.example.hikeit.trails.data

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.data.model.TrailDetails
import com.example.hikeit.trails.data.networking.TrailDataSource
import com.example.hikeit.trails.domain.Trail
import com.example.hikeit.trails.domain.TrailRepository

class TrailRepositoryImpl(
    private val trailDataSource: TrailDataSource
) : TrailRepository{

    override suspend fun getTrails(): Result<List<Trail>, NetworkError> {
        return trailDataSource.getTrails()
    }

    override suspend fun getTrailDetails(trailId: String): Result<TrailDetails, NetworkError> {
        return Result.Success(TrailDetails())
    }
}