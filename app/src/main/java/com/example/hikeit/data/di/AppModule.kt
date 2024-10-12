package com.example.hikeit.data.di

import com.example.hikeit.data.remote.InMemoryTrailApi
import com.example.hikeit.data.remote.TrailApi
import com.example.hikeit.data.repository.TrailDataRepository
import com.example.hikeit.data.repository.TrailDataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideTrailApi(): TrailApi {
        return InMemoryTrailApi()
    }

    @Provides
    fun trailDataRepository() : TrailDataRepository {
        return TrailDataRepositoryImpl(provideTrailApi())
    }
}