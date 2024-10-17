package com.example.hikeit.trails.data.networking

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.domain.Trail

interface TrailDataSource {
    suspend fun getTrails() : Result<List<Trail>, NetworkError>
}