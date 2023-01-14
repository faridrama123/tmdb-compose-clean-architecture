package com.example.tmdbapicompose.data.repository.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ReviewResponse (
    val id: Long,
    val page: Long,
    val results: List<ReviewObjResponse>,
    val total_pages: Long,
    val total_results: Long
)

@Parcelize
data class ReviewObjResponse (
    val author: String,

    val author_details: AuthorDetailsResponse,

    val content: String,

    val created_at: String,

    val id: String,

    val updated_at: String,

    val url: String
): Parcelable

@Parcelize
data class AuthorDetailsResponse (
    val name: String,
    val username: String,

    val avatar_path: String? = "",

    val rating: Double? = 0.0
): Parcelable