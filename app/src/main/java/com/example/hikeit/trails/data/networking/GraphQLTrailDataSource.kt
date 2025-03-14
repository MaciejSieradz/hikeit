package com.example.hikeit.trails.data.networking

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.apollographql.apollo.api.Optional
import com.example.hikeit.AddReviewMutation
import com.example.hikeit.AllTrailsQuery
import com.example.hikeit.CreateTrailMutation
import com.example.hikeit.DeleteTrailMutation
import com.example.hikeit.GetTrailByIdQuery
import com.example.hikeit.MarkedTrailsQuery
import com.example.hikeit.SaveTrailMutation
import com.example.hikeit.UnmarkTrailMutation
import com.example.hikeit.UpdateTrailMutation
import com.example.hikeit.UserTrailsQuery
import com.example.hikeit.apollo.apolloClient
import com.example.hikeit.core.data.xml.parseGpxFile
import com.example.hikeit.core.domain.util.NetworkError
import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.data.mappers.toTrail
import com.example.hikeit.trails.data.networking.dto.EstimatedHikingTimeDto
import com.example.hikeit.trails.data.networking.dto.TrailDto
import com.example.hikeit.trails.data.security.AppState
import com.example.hikeit.trails.domain.Comment
import com.example.hikeit.trails.domain.EstimatedHikingTime
import com.example.hikeit.trails.domain.Review
import com.example.hikeit.trails.domain.Trail
import com.example.hikeit.trails.domain.TrailDetails
import com.example.hikeit.trails.domain.TrailForm
import com.example.hikeit.type.EstimatedHikingTimeInput
import com.example.hikeit.type.ReviewInput
import com.example.hikeit.type.TrailInput
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneId
import java.util.UUID

class GraphQLTrailDataSource : TrailDataSource {

    private val client = HttpClient()

    override suspend fun getTrails(): Result<List<Trail>, NetworkError> {
        val response = apolloClient.query(AllTrailsQuery()).execute()

        if (response.hasErrors()) {
            return Result.Error(NetworkError.SERVER_ERROR)
        }

        Log.d("Getting all trails", response.data.toString())
        val trails = response.data!!.getAllTrails!!.filterNotNull().map {
            val photo = if (!it.photos.isNullOrEmpty()) it.photos[0]!! else ""
            TrailDto(
                id = it.id!!,
                photoUrl = photo,
                title = it.title!!,
                rating = it.rating!!,
                difficulty = it.difficulty!!,
                distance = it.distance!!,
                estimatedHikingTime = EstimatedHikingTimeDto(
                    hours = it.estimatedHikingTime?.hours ?: 0,
                    minutes = it.estimatedHikingTime?.minutes ?: 0
                ),
            ).toTrail()
        }

        return Result.Success(trails)
    }

    override suspend fun getSavedTrails(): Result<List<Trail>, NetworkError> {
        val response = apolloClient.query(MarkedTrailsQuery()).execute()

        if (response.hasErrors()) {
            return Result.Error(NetworkError.SERVER_ERROR)
        }

        val trails = response.data!!.getSavedTrails!!.filterNotNull().map {
            val photo = if (!it.photos.isNullOrEmpty()) it.photos[0]!! else ""
            TrailDto(
                id = it.id!!,
                photoUrl = photo,
                title = it.title!!,
                rating = it.rating!!,
                difficulty = it.difficulty!!,
                distance = it.distance!!,
                estimatedHikingTime = EstimatedHikingTimeDto(
                    hours = it.estimatedHikingTime?.hours ?: 0,
                    minutes = it.estimatedHikingTime?.minutes ?: 0
                ),
            ).toTrail()
        }

        return Result.Success(trails)
    }

    override suspend fun getUserTrails(): Result<List<Trail>, NetworkError> {
        val response = apolloClient.query(UserTrailsQuery()).execute()

        if (response.hasErrors()) {
            return Result.Error(NetworkError.SERVER_ERROR)
        }

        val trails = response.data!!.getUserTrails!!.filterNotNull().map {
            val photo = if (!it.photos.isNullOrEmpty()) it.photos[0]!! else ""
            TrailDto(
                id = it.id!!,
                photoUrl = photo,
                title = it.title!!,
                rating = it.rating!!,
                difficulty = it.difficulty!!,
                distance = it.distance!!,
                estimatedHikingTime = EstimatedHikingTimeDto(
                    hours = it.estimatedHikingTime?.hours ?: 0,
                    minutes = it.estimatedHikingTime?.minutes ?: 0
                ),
            ).toTrail()
        }

        return Result.Success(trails)
    }

    override suspend fun removeTrail(id: String): Result<Boolean, NetworkError> {
        val response = apolloClient.mutation(DeleteTrailMutation(id)).execute()

        if (response.hasErrors() || response.data!!.deleteTrail == false) {
                return Result.Error(NetworkError.SERVER_ERROR)
        }

        return Result.Success(true)
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override suspend fun getTrailDetails(id: String): Result<TrailDetails, NetworkError> {
        val response = apolloClient.query(GetTrailByIdQuery(id)).execute()

        if (response.hasErrors()) {
            return Result.Error(NetworkError.SERVER_ERROR)
        }

        val trail = response.data!!.getTrailById!!

        val route = client.get(trail.gpxUrl!!).bodyAsText()

        return Result.Success(
            TrailDetails(
                id = trail.id!!,
                title = trail.title!!,
                difficulty = trail.difficulty!!,
                rating = trail.rating!!,
                numberOfRatings = trail.numberOfRatings!!,
                distance = trail.distance!!,
                elevationGain = trail.elevationGain!!,
                maxHeight = trail.maxHeight!!,
                estimatedHikingTime = EstimatedHikingTime(
                    hours = trail.estimatedHikingTime?.hours ?: 0,
                    minutes = trail.estimatedHikingTime?.minutes ?: 0
                ),
                description = trail.description!!,
                comments = trail.comments?.map {
                    Comment(
                        it!!.userAvatarUrl!!,
                        it.username!!,
                        it.rating!!,
                        getCommentDate(Instant.parse(it.commentDate!!)),
                        it.comment,
                        it.photosUrl!!.filterNotNull()
                    )
                } ?: emptyList(),
                route = route.parseGpxFile(),
                trailPhotos = trail.photos!!.filterNotNull(),
                isMarked = trail.isMarked!!
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun getCommentDate(createdAt: Instant) : String {
        val fromDate = LocalDate.ofInstant(createdAt, ZoneId.systemDefault())
        val today = LocalDate.now()

        val period = Period.between(fromDate, today)

        if (period.years > 0) {
            if (period.years == 1) return "Opublikowano rok temu"
            if (period.years in 2..4) return "Opublikowano ${period.years} lata temu"
            return "Opublikowano ${period.years} lat temu"
        }

        if (period.months > 0) {
            if (period.months == 1) return "Opublikowano miesiąc temu"
            if(period.months in 2..4) return "Opublikowano ${period.months} miesiące temu"
            return "Opublikowano ${period.months} miesięcy temu"
        }

        if (period.days <= 1) return "Opublikowano dzień temu"

        return "Opublikowano ${period.days} dni temu"
    }

    override suspend fun saveTrail(trailForm: TrailForm): Result<TrailDto, NetworkError> {

        val filename = UUID.randomUUID().toString()

        val gpxResponse: HttpResponse = client.submitFormWithBinaryData(
            url = "http://10.0.2.2:8080/api/v1/upload",
            formData = formData {
                append("file", trailForm.gpx, Headers.build {
                    append(HttpHeaders.ContentType, "multipart/form-data")
                    append(HttpHeaders.ContentDisposition, "filename=\"$filename.gpx\"")
                })
            }
        ) {
            header("Authorization", "Bearer ${AppState.token}")
        }

        val imgResponse = uploadPhotos(trailForm.photos)

        val response = apolloClient.mutation(
            CreateTrailMutation(
                trailInput = TrailInput(
                    title = trailForm.title,
                    description = trailForm.description,
                    estimatedHikingTime = EstimatedHikingTimeInput(
                        hours = trailForm.estimatedHikingTime.hours,
                        minutes = trailForm.estimatedHikingTime.minutes
                    ),
                    elevation = trailForm.elevation,
                    distance = trailForm.distance,
                    maxHeight = trailForm.maxElevation,
                    difficulty = trailForm.difficulty.difficultyName,
                    gpxTrailUrl = gpxResponse.bodyAsText(),
                    photosUrl = Optional.present(imgResponse)
                )
            )
        ).execute()

        if (response.hasErrors()) {
            return Result.Error(NetworkError.SERVER_ERROR)
        }

        val trail = response.data!!.createTrail!!

        return Result.Success(
            TrailDto(
                id = trail.id!!,
                photoUrl = trail.photos!![0]!!,
                title = trail.title!!,
                rating = 0.0,
                difficulty = trail.difficulty!!,
                distance = trail.distance!!,
                estimatedHikingTime = EstimatedHikingTimeDto(
                    trail.estimatedHikingTime!!.hours!!,
                    trail.estimatedHikingTime.minutes!!
                )
            )
        )
    }

    override suspend fun updateTrail(
        id: String,
        trailForm: TrailForm
    ): Result<TrailDto, NetworkError> {

        val filename = UUID.randomUUID().toString()

        val gpxResponse: HttpResponse = client.submitFormWithBinaryData(
            url = "http://10.0.2.2:8080/api/v1/upload",
            formData = formData {
                append("file", trailForm.gpx, Headers.build {
                    append(HttpHeaders.ContentType, "multipart/form-data")
                    append(HttpHeaders.ContentDisposition, "filename=\"$filename.gpx\"")
                })
            }
        ) {
            header("Authorization", "Bearer ${AppState.token}")
        }

        val imgResponse = uploadPhotos(trailForm.photos)

        val response = apolloClient.mutation(
            UpdateTrailMutation(
                trailId = id,
                trail = TrailInput(
                    title = trailForm.title,
                    description = trailForm.description,
                    estimatedHikingTime = EstimatedHikingTimeInput(
                        hours = trailForm.estimatedHikingTime.hours,
                        minutes = trailForm.estimatedHikingTime.minutes
                    ),
                    elevation = trailForm.elevation,
                    distance = trailForm.distance,
                    maxHeight = trailForm.maxElevation,
                    difficulty = trailForm.difficulty.difficultyName,
                    gpxTrailUrl = gpxResponse.bodyAsText(),
                    photosUrl = Optional.present(imgResponse)
                )
            )
        ).execute()

        if (response.hasErrors()) {
            return Result.Error(NetworkError.SERVER_ERROR)
        }

        val trail = response.data!!.updateTrail!!

        return Result.Success(
            TrailDto(
                id = trail.id!!,
                photoUrl = trail.photos!![0]!!,
                title = trail.title!!,
                rating = 0.0,
                difficulty = trail.difficulty!!,
                distance = trail.distance!!,
                estimatedHikingTime = EstimatedHikingTimeDto(
                    trail.estimatedHikingTime!!.hours!!,
                    trail.estimatedHikingTime.minutes!!
                )
            )
        )
    }

    override suspend fun addReview(trailId: String, review: Review): Result<Boolean, NetworkError> {
        val imgResponse = uploadPhotos(review.photos)

        val response = apolloClient.mutation(
            AddReviewMutation(
                trailId = trailId,
                reviewInput = ReviewInput(
                    mark = review.mark,
                    description = Optional.present(review.description),
                    photosUrl = Optional.present(imgResponse)
                )
            )
        ).execute()

        if (response.hasErrors()) {
            return Result.Error(NetworkError.SERVER_ERROR)
        }

        return Result.Success(true)
    }

    override suspend fun markTrailAsSaved(trailId: String): Result<Boolean, NetworkError> {
        val response = apolloClient.mutation(
            SaveTrailMutation(
                trailId = trailId
            )
        ).execute()

        if (response.hasErrors()) {
            return Result.Error(NetworkError.SERVER_ERROR)
        }

        return Result.Success(true)
    }

    override suspend fun unmarkTrail(trailId: String): Result<Boolean, NetworkError> {
        val response = apolloClient.mutation(
            UnmarkTrailMutation(
                trailId = trailId
            )
        ).execute()

        if (response.hasErrors()) {
            return Result.Error(NetworkError.SERVER_ERROR)
        }

        return Result.Success(false)
    }

    private suspend fun uploadPhotos(photos: List<ByteArray>): List<String>? {
        val imageResponse: HttpResponse = client.submitFormWithBinaryData(
            url = "http://10.0.2.2:8080/api/v1/upload-images",
            formData = formData {
                photos.forEach {

                    val imgName = UUID.randomUUID().toString()

                    append("files", it, Headers.build {
                        append(HttpHeaders.ContentType, "multipart/form-data")
                        append(HttpHeaders.ContentDisposition, "filename=\"$imgName.jpg\"")
                    })
                }
            }
        ) {
            header("Authorization", "Bearer ${AppState.token}")
        }

        return Json.decodeFromString<List<String>?>(imageResponse.bodyAsText())
    }
}