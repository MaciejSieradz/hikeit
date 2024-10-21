package com.example.hikeit.trails.data

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.domain.TrailDetails
import com.example.hikeit.trails.data.networking.TrailDataSource
import com.example.hikeit.trails.domain.EstimatedHikingTime
import com.example.hikeit.trails.domain.Trail
import com.example.hikeit.trails.domain.TrailRepository

class TrailRepositoryImpl(
    private val trailDataSource: TrailDataSource
) : TrailRepository {

    private val trailDetails = listOf(
        TrailDetails(
            id = "1",
            trailPhotoUrl = "https://tatromaniak.pl/wp-content/uploads/2015/07/szpiglasowy_rafal_ociepka_360_kopia2.jpg",
            title = "Szpiglasowy Wierch do Doliny pięciu stawów",
            difficulty = "Zaawansowany",
            rating = 4.8,
            numberOfRatings = 156,
            distance = 24.0,
            elevationGain = 3071,
            estimatedHikingTime = EstimatedHikingTime(8, 14),
            maxHeight = 2177,
            description = "Piękny szlak zaczynający się przy Morskim Oku, z którego przechodzimy do" +
                    "wejścia na Szpiglasowy Wierch, z którego rozpościera się jeden z najpiękniejszych " +
                    "widoków na polskie Tatry.",
            comments = emptyList()
        ),
        TrailDetails(
            id = "2",
            trailPhotoUrl = "https://www.e-wczasy.pl/blog/wp-content/uploads/2020/10/szlak-tatry.jpg",
            title = "Wołowiec od Zawoi",
            difficulty = "Umiarkowany",
            rating = 5.0,
            numberOfRatings = 752,
            distance = 12.0,
            elevationGain = 1800,
            estimatedHikingTime = EstimatedHikingTime(5, 30),
            maxHeight = 1892,
            description = "Umiarkowany szlak w Tatrach Zachodnich, z głownym szczytem Wołowiec. Trasa " +
                    "prowadzi przez malownicze lasy, a z samego Wołowca prezentuje się piękny widok na Tatry Wysokie.",
            comments = emptyList()
        ),
        TrailDetails(
            id = "3",
            trailPhotoUrl = "https://www.e-horyzont.pl/media/mageplaza/blog/post/r/y/rysy-01.jpg",
            title = "Rysy od polskiej strony",
            difficulty = "Zaawansowany",
            rating = 4.8,
            numberOfRatings = 1752,
            distance = 18.0,
            elevationGain = 4800,
            estimatedHikingTime = EstimatedHikingTime(6, 40),
            maxHeight = 2499,
            description = "Wejście na najwyższy szczyt w Polsce - Rysy od polskiej strony. Droga zaczyna się przy Morskim Oku" +
                    ", przechodzi przez Czarny Staw i kończy się na szczycie.",

            comments = emptyList()
        ),
    )

    override suspend fun getTrails(): Result<List<Trail>, NetworkError> {
        return trailDataSource.getTrails()
    }

    override suspend fun getTrailDetails(trailId: String): Result<TrailDetails, NetworkError> {
        val trail = trailDetails.first { it.id == trailId }
        return Result.Success(
            trail
        )
    }
}