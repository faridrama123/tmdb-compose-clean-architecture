package com.example.tmdbapicompose.data.repository.response


import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class GenreMovieResponse (
    val genres: List<GenreMovieObjResponse>,
)

@Parcelize
data class GenreMovieObjResponse (
    val id: Int?,
    val name: String?,
): Parcelable