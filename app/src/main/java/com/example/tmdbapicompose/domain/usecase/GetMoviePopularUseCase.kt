package com.example.tmdbapicompose.domain.usecase

import com.example.tmdbapicompose.data.repository.Repository
import com.example.tmdbapicompose.domain.models.MoviePopularEntity
import com.example.tmdbapicompose.utils.Resource


class GetMoviePopularUseCase(
    val repository: Repository
) {
    suspend operator fun invoke(genre: String, page: Int): Resource<MoviePopularEntity> {
        return repository.getMovieList(genre, page)
    }
}