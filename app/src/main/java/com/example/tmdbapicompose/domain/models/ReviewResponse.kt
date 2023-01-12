package com.example.tmdbapicompose.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ReviewResponse (
    val id: Long,
    val page: Long,
    val results: List<ReviewResponseList>,
    val total_pages: Long,
    val total_results: Long
) {

}
@Parcelize
data class ReviewResponseList (
    val author: String,

    val author_details: AuthorDetails,

    val content: String,

    val created_at: String,

    val id: String,

    val updated_at: String,

    val url: String
): Parcelable

@Parcelize
data class AuthorDetails (
    val name: String,
    val username: String,

    val avatar_path: String? = "",

    val rating: Double? = 0.0
): Parcelable