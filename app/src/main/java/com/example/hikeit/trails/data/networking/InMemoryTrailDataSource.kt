package com.example.hikeit.trails.data.networking

import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.data.mappers.toTrail
import com.example.hikeit.trails.data.networking.dto.EstimatedHikingTimeDto
import com.example.hikeit.trails.data.networking.dto.TrailDto
import com.example.hikeit.trails.domain.Comment
import com.example.hikeit.trails.domain.EstimatedHikingTime
import com.example.hikeit.trails.domain.Trail
import com.example.hikeit.trails.domain.TrailDetails

class InMemoryTrailDataSource() : TrailDataSource {

    override suspend fun getTrails(): Result<List<Trail>, NetworkError> {
        val trails = trails.map {
            TrailDto(
                id = it.id,
                photoUrl = it.trailPhotoUrl,
                title = it.title,
                mountains = "Tatry",
                distance = it.distance,
                difficulty = it.difficulty,
                estimatedHikingTime = EstimatedHikingTimeDto(
                    it.estimatedHikingTime.hours,
                    it.estimatedHikingTime.minutes
                ),
                rating = it.rating
            )
        }
        return Result.Success(trails.map { it.toTrail() })
    }

    override suspend fun getTrailDetails(id: String): Result<TrailDetails, NetworkError> {
        return Result.Success(trails.first { it.id == id })
    }

    override suspend fun saveTrail(trailDetails: TrailDetails): Result<Boolean, NetworkError> {
        trails = trails.plus(trailDetails)

        return Result.Success(true)
    }
}

private var trails = listOf(
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
        comments = listOf(
            Comment(
                userAvatarUrl = "123",
                username = "Maciej Sieradz",
                rating = 4,
                commentDate = "1 miesiąc temu",
                comment =
                "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
                        "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!"
            ),
            Comment(
                userAvatarUrl = "123",
                username = "Maciej Sieradz",
                rating = 4,
                commentDate = "1 miesiąc temu",
                comment =
                "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
                        "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!"
            ),
            Comment(
                userAvatarUrl = "123",
                username = "Maciej Sieradz",
                rating = 4,
                commentDate = "1 miesiąc temu",
                comment =
                "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
                        "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!"
            )
        )
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
        comments = listOf(
            Comment(
                userAvatarUrl = "123",
                username = "Marcin Przypadkowy",
                rating = 5,
                commentDate = "3 miesiące temu",
                comment = "Bardzo przyjemna trasa, niesamowity widok z Wołowca!"
            ),
            Comment(
                userAvatarUrl = "123",
                username = "Anita jakaś",
                rating = 4,
                commentDate = "Wczoraj",
                comment =
                "Poszliśmy na ten szlak z naszym 12 letnim dzieckiem i dało radę bez problemu! Szlak nie jest wcale taki trudny"
            ),
            Comment(
                userAvatarUrl = "123",
                username = "Maciej Sieradz",
                rating = 4,
                commentDate = "1 miesiąc temu",
                comment = "Całkiem przyjemna trasa, dosyć krótka, polecam podejście z drugiej strony."
            )
        )
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

        comments = listOf(
            Comment(
                userAvatarUrl = "123",
                username = "Marcin Przypadkowy",
                rating = 5,
                commentDate = "3 miesiące temu",
                comment = "Bardzo przyjemna trasa, niesamowity widok z Wołowca!"
            ),
            Comment(
                userAvatarUrl = "123",
                username = "Anita jakaś",
                rating = 4,
                commentDate = "Wczoraj",
                comment =
                "Poszliśmy na ten szlak z naszym 12 letnim dzieckiem i dało radę bez problemu! Szlak nie jest wcale taki trudny"
            ),
            Comment(
                userAvatarUrl = "123",
                username = "Maciej Sieradz",
                rating = 4,
                commentDate = "1 miesiąc temu",
                comment = "Całkiem przyjemna trasa, dosyć krótka, polecam podejście z drugiej strony."
            )
        )
    ),
)
