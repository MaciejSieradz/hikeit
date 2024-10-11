package com.example.hikeit.data.di

import com.example.hikeit.data.remote.InMemoryTrailApi
import com.example.hikeit.data.remote.TrailApi
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
}