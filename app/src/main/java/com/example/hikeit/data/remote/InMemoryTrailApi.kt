package com.example.hikeit.data.remote

import com.example.hikeit.data.TrailInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InMemoryTrailApi : TrailApi {

    override suspend fun getAllTrails(): List<TrailInfo> {
        delay(3000)
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
        return trails
    }
}