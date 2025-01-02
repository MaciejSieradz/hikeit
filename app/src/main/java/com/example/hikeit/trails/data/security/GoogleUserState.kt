package com.example.hikeit.trails.data.security

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

object AppState {
    var user: GoogleUserState? = null
    var token: String? = null
    var trail: List<LatLng>? = null
}

data class GoogleUserState(
    val email: String,
    val name: String?,
    val avatarUrl: Uri?
)
