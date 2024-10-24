package com.example.hikeit.trails.presentation.models

import com.example.hikeit.trails.domain.EstimatedHikingTime
import com.example.hikeit.trails.domain.Trail
import junit.framework.TestCase.assertEquals
import org.junit.Test

class TrailUiTest {

    @Test
    fun trail_toTrailUi_correctMapping() {
        val trail = Trail(
            id = "123",
            photoUrl = "123",
            title = "Test Title",
            mountains = "Test Mountains",
            rating = 5.0,
            difficulty = "Hard",
            distance = 10.0,
            estimatedHikingTime = EstimatedHikingTime(2,20)
        )

        val trailUi = TrailUi(
            id = "123",
            photoUrl = "123",
            title = "Test Title",
            mountains = "Test Mountains",
            trailAdditionalInfo = "5,0 - Hard - Szac. 2 godz. 20 min."
        )

        assertEquals(trailUi, trail.toTrailUi())
    }
}