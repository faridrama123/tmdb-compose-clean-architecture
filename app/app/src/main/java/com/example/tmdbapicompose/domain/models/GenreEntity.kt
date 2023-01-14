package com.example.tmdbapicompose.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class GenreMovieEntity (
    val genres: List<GenreMovieObjEntity>,
)

@Parcelize
data class GenreMovieObjEntity (
    val id: Int?,
    val name: String?,
): Parcelable