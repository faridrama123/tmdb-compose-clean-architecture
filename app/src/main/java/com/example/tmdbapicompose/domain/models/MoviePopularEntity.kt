package com.example.tmdbapicompose.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MoviePopularEntity(
    val page: Int,
    val results: List<MoviePopularObjEntity>,
    val total_pages: Int,
    val total_results: Int
)
@Parcelize
data class MoviePopularObjEntity(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int
) : Parcelable