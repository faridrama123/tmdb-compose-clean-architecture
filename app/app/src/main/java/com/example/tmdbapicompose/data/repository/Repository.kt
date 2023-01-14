package com.example.tmdbapicompose.data.repository

import com.example.tmdbapicompose.domain.models.GenreMovieEntity
import com.example.tmdbapicompose.domain.models.MoviePopularEntity
import com.example.tmdbapicompose.domain.models.ReviewEntity
import com.example.tmdbapicompose.domain.models.VideoMovieEntity
import com.example.tmdbapicompose.utils.Resource
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSourceImp,
){

    suspend fun getMovieList(genre: String, page: Int): Resource<MoviePopularEntity> {
        return remote.getMovieList(genre, page)
    }
    suspend fun getReviewMovie(id: Int,  page: Int): Resource<ReviewEntity> {
        return remote.getReview(id, page)
    }
    suspend fun getGenre(): Resource<GenreMovieEntity> {
        return remote.getGenre()
    }
    suspend fun getVideo(id: Int): Resource<VideoMovieEntity> {
        return remote.getVideo(id)
    }
}