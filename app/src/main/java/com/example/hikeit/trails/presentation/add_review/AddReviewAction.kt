package com.example.hikeit.trails.presentation.add_review

import android.net.Uri

sealed interface AddReviewAction {
    data class MarkChanged(val mark: Int) : AddReviewAction
    data class PhotosSelected(val photosUri: List<Uri>, val photos: List<ByteArray>) : AddReviewAction
    data class RemovePhoto(val photoUri: Uri) : AddReviewAction
    data class DescriptionChanged(val description: String) : AddReviewAction

    data object AddReview : AddReviewAction
}