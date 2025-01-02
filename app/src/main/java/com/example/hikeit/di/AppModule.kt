package com.example.hikeit.di

import com.example.hikeit.trails.data.TrailRepositoryImpl
import com.example.hikeit.trails.data.networking.GraphQLTrailDataSource
import com.example.hikeit.trails.data.networking.TrailDataSource
import com.example.hikeit.trails.domain.TrailRepository
import com.example.hikeit.trails.presentation.add_review.AddReviewViewModel
import com.example.hikeit.trails.presentation.create_trail.CreateTrailViewModel
import com.example.hikeit.trails.presentation.profile.ProfileViewModel
import com.example.hikeit.trails.presentation.saved_trail_list.SavedTrailListViewModel
import com.example.hikeit.trails.presentation.trail_detail.TrailDetailViewmodel
import com.example.hikeit.trails.presentation.trail_list.TrailListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    singleOf(::GraphQLTrailDataSource).bind<TrailDataSource>()
    singleOf(::TrailRepositoryImpl).bind<TrailRepository>()

    viewModelOf(::TrailDetailViewmodel)
    viewModelOf(::TrailListViewModel)
    viewModelOf(::CreateTrailViewModel)
    viewModelOf(::AddReviewViewModel)
    viewModelOf(::SavedTrailListViewModel)
    viewModelOf(::ProfileViewModel)
}

