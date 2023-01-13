package com.example.tmdbapicompose.data.apiCall

import com.example.tmdbapicompose.data.repository.response.DataMovieResponse
import com.example.tmdbapicompose.domain.models.GenreMovieResponse
import com.example.tmdbapicompose.domain.models.MovieResponse
import com.example.tmdbapicompose.domain.models.ReviewResponse
import com.example.tmdbapicompose.domain.models.VideoMovieResponse
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
        @Path("Id") movieId: Int,
        @Query("page") page: Int): ReviewResponse

    @GET("movie/{Id}/videos")
    suspend fun getVideoMovie(
        @Path("Id") movieId: Int): VideoMovieResponse

}