package com.example.tmdbapicompose.domain.di

import android.content.Context
import com.example.tmdbapicompose.data.repository.Repository
import com.example.tmdbapicompose.domain.usecase.*

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesUseCases(repository: Repository): UseCases{
        return UseCases(
            getMoviePopularUseCase = GetMoviePopularUseCase(repository),
            getReviewMovieUseCase = GetReviewMovieUseCase(repository),
            getGenrUseCase = GetGenrUseCase(repository),
            getVideoUseCase = GetVideoUseCase(repository),
        )
    }
}