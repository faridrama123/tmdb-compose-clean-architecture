package com.example.tmdbapicompose.domain.repository

import com.example.tmdbapicompose.data.repository.response.ReviewResponse
import com.example.tmdbapicompose.domain.models.GenreMovieEntity
import com.example.tmdbapicompose.domain.models.MoviePopularEntity
import com.example.tmdbapicompose.domain.models.ReviewEntity
import com.example.tmdbapicompose.domain.models.VideoMovieEntity
import com.example.tmdbapicompose.utils.Resource
import kotlinx.coroutines.flow.Flow


interface IRemoteDataSource {
    suspend fun getMovieList(genre: String, page : Int) : Resource<MoviePopularEntity>
    suspend fun getReview(id: Int, page : Int) : Resource<ReviewEntity>
    suspend fun getGenre() : Resource<GenreMovieEntity>
    suspend fun getVideo(id: Int) : Resource<VideoMovieEntity>

}