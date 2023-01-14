package com.example.tmdbapicompose.data.apiCall

import com.example.tmdbapicompose.data.repository.response.GenreMovieResponse
import com.example.tmdbapicompose.data.repository.response.MoviePopularResponse
import com.example.tmdbapicompose.data.repository.response.ReviewResponse
import com.example.tmdbapicompose.data.repository.response.VideoMovieResponse
import com.example.tmdbapicompose.domain.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * An Interface which contains All the API requests
 */

interface UserApi {

    @GET("movie/popular")
    suspend fun getMovieList2(
        @Query("with_genres") genre: String,
        @Query("page") page: Int): MoviePopularResponse

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