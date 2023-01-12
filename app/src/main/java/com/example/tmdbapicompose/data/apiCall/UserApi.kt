package com.example.tmdbapicompose.data.apiCall

import com.example.tmdbapicompose.domain.models.GenreMovieResponse
import com.example.tmdbapicompose.domain.models.MovieResponse
import com.example.tmdbapicompose.domain.models.ReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * An Interface which contains All the API requests
 */

interface UserApi {
    @GET("movie/popular")
    suspend fun getMovieList(
        @Query("with_genres") genre: String,
        @Query("page") page: Int): MovieResponse

    @GET("genre/movie/list")
    suspend fun getGenreMovie(): GenreMovieResponse

    @GET("movie/{Id}/reviews")
    suspend fun getMovieReview(
        @Path("Id") movieId: Int): ReviewResponse

}