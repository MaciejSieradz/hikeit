package com.example.hikeit.trails.presentation.models

import com.example.hikeit.trails.domain.EstimatedHikingTime
import com.example.hikeit.trails.domain.TrailDetails
import junit.framework.TestCase.assertEquals
import org.junit.Test

class TrailDetailUiTest {

    @Test
    fun toTrailDetailsUi_correctMapping() {

        val trailDetailUi = TrailDetailUi(
            trailPhotoUrl = "test",
            title = "Title test",
            difficulty = "Zaawansowany",
            rating = "5,0 (100)",
            distance = "12,0 km",
            elevationGain = "1000 m",
            maxHeight = "2000 m",
            estimatedHikingTime = "5 godz. 50 min.",
            description = "Description"
        )

        val trailDetail = TrailDetails(
            id = "1",
            trailPhotoUrl = "test",
            title = "Title test",
            difficulty = "Zaawansowany",
            rating = 5.0,
            numberOfRatings = 100,
            distance = 12.0,
            elevationGain = 1000,
            maxHeight = 2000,
            estimatedHikingTime = EstimatedHikingTime(5,50),
            description = "Description",
            comments = emptyList()
        )

        assertEquals(trailDetailUi, trailDetail.toTrailDetailsUi())
    }
}