package com.example.hikeit.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

data class TrailInfo(
    val photo: String,
    val title: String,
    val mountains: String,
    val stars: Double,
    val difficulty: String,
    val distance: Double,
    val hours: Int,
    val minutes: Int,
) {
    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun greeting() : String {
        val response = client.get("https://ktor.io/docs/").body<TrailInfo>()
        return ""
    }
}

