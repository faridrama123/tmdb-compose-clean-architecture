package com.example.tmdbapicompose.domain.usecase

import com.example.tmdbapicompose.data.repository.Repository
import com.example.tmdbapicompose.domain.models.GenreMovieEntity
import com.example.tmdbapicompose.utils.Resource

class GetGenrUseCase(
    val repository: Repository
) {
    suspend operator fun invoke(): Resource<GenreMovieEntity> {
        return repository.getGenre()
    }
}