package com.example.tmdbapicompose.domain.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


data class VideoMovieEntity (
    val id: Long,
    val results: List<VideoMovieObjEntity>
)

@Parcelize
data class VideoMovieObjEntity (
    @Json(name = "iso_639_1")
    val iso639_1: String?,

    @Json(name = "iso_3166_1")
    val iso3166_1: String?,

    val name: String?,
    val key: String?,
    val site: String?,
    val size: Long?,
    val type: String?,
    val official: Boolean?,

    @Json(name = "published_at")
    val publishedAt: String?,

    val id: String
): Parcelable