package com.example.tmdbapicompose.domain.usecase

import com.example.tmdbapicompose.data.repository.Repository
import com.example.tmdbapicompose.domain.models.ReviewEntity
import com.example.tmdbapicompose.domain.models.VideoMovieEntity
import com.example.tmdbapicompose.utils.Resource

class GetVideoUseCase (
    val repository: Repository
) {
    suspend operator fun invoke(id: Int): Resource<VideoMovieEntity> {
        return repository.getVideo(id)
    }
}