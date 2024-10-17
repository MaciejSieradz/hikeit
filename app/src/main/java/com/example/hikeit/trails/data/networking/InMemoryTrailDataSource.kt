package com.example.hikeit.trails.data.networking

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.data.mappers.toTrail
import com.example.hikeit.trails.data.networking.dto.EstimatedHikingTimeDto
import com.example.hikeit.trails.data.networking.dto.TrailDto
import com.example.hikeit.trails.domain.Trail

class InMemoryTrailDataSource() : TrailDataSource {

    override suspend fun getTrails(): Result<List<Trail>, NetworkError> {
        val trails = listOf(
            TrailDto(
                "",
                "https://tatromaniak.pl/wp-content/uploads/2015/07/szpiglasowy_rafal_ociepka_360_kopia2.jpg",
                "Szpiglasowy Wierch do Doliny Pięciu Stawów",
                "Tatry",
                4.8,
                "Zaawansowany",
                24.0,
                EstimatedHikingTimeDto(8, 14)
            ),
            TrailDto(
                "",
                "https://www.e-wczasy.pl/blog/wp-content/uploads/2020/10/szlak-tatry.jpg",
                "Wołowiec od Zawoi",
                "Tatry Zachodnie",
                5.0,
                "Umiarkowany",
                12.0,
                EstimatedHikingTimeDto(8, 14)
            ),
            TrailDto(
                "",
                "https://www.e-horyzont.pl/media/mageplaza/blog/post/r/y/rysy-01.jpg",
                "Rysy od polskiej strony",
                "Tatry",
                4.8,
                "Zaawansowany",
                18.0,
                EstimatedHikingTimeDto(6, 40)
            )
        )
        return Result.Success(trails.map { it.toTrail() })
    }
}