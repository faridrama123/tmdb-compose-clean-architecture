package com.example.tmdbapicompose.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class GenreMovieResponse (
    val genres: List<GenreMovie>,
) {
}
@Parcelize
data class GenreMovie (
    val id: Int,
    val name: String,
): Parcelable