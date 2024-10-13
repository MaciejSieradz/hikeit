package com.example.hikeit

import com.example.hikeit.data.TrailInfo
import com.example.hikeit.data.di.AppModule
import com.example.hikeit.data.model.TrailDetails
import com.example.hikeit.data.repository.TrailDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
@Module
class FakeAppModule {

    @Provides
    fun trailDataRepository() : TrailDataRepository {
        return FakeTrailDataRepositoryImpl()
    }

}

class FakeTrailDataRepositoryImpl : TrailDataRepository {
    private val _trailData = flow { emit(trails)  }

    override val trailData: Flow<List<TrailInfo>> = _trailData.filterNotNull()

    override suspend fun getTrailDetails(trialId: String): TrailDetails {
        return TrailDetails()
    }
}

val trails = listOf(
    TrailInfo(
        "https://tatromaniak.pl/wp-content/uploads/2015/07/szpiglasowy_rafal_ociepka_360_kopia2.jpg",
        "Szpiglasowy Wierch do Doliny Pięciu Stawów",
        "Tatry",
        4.8,
        "Zaawansowany",
        24.0,
        8,
        14
    ),
    TrailInfo(
        "https://www.e-wczasy.pl/blog/wp-content/uploads/2020/10/szlak-tatry.jpg",
        "Wołowiec od Zawoi",
        "Tatry Zachodnie",
        5.0,
        "Umiarkowany",
        12.0,
        5,
        3
    ),
    TrailInfo(
        "https://www.e-horyzont.pl/media/mageplaza/blog/post/r/y/rysy-01.jpg",
        "Rysy od polskiej strony",
        "Tatry",
        4.8,
        "Zaawansowany",
        18.0,
        6,
        40
    )
)