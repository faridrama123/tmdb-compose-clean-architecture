package com.example.tmdbapicompose.domain.usecase

import com.example.tmdbapicompose.data.repository.Repository
import com.example.tmdbapicompose.domain.models.ReviewEntity
import com.example.tmdbapicompose.utils.Resource


class GetReviewMovieUseCase(
    val repository: Repository
) {
    suspend operator fun invoke(id: Int, page: Int): Resource<ReviewEntity> {
        return repository.getReviewMovie(id, page)
    }
}