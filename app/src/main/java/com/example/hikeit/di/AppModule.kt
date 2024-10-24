package com.example.hikeit.di

import com.example.hikeit.trails.data.TrailRepositoryImpl
import com.example.hikeit.trails.data.networking.InMemoryTrailDataSource
import com.example.hikeit.trails.data.networking.TrailDataSource
import com.example.hikeit.trails.domain.TrailRepository
import com.example.hikeit.trails.presentation.trail_list.TrailListViewModel
import com.example.hikeit.trails.presentation.trail_detail.TrailDetailViewmodel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    singleOf(::InMemoryTrailDataSource).bind<TrailDataSource>()
    singleOf(::TrailRepositoryImpl).bind<TrailRepository>()

    viewModelOf(::TrailDetailViewmodel)
    viewModelOf(::TrailListViewModel)
}

