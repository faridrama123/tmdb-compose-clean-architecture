package com.example.tmdbapicompose.domain.utils

import com.example.tmdbapicompose.data.repository.response.ListDataMovieResponse

object DataMapper {


    fun mapDomainToEntity(it: ListDataMovieResponse) = com.example.tmdbapicompose.domain.models.Result(
            adult = it.adult,
            backdrop_path = it.backdrop_path,
            genre_ids = it.genre_ids,
            id = it.id,
            original_language = it.original_language,
            original_title = it.original_title,
            overview =  it.overview,
            popularity =  it.popularity,
            poster_path = it.poster_path,
            release_date = it.release_date,
            title = it.title,
            video = it.video,
            vote_average = it.vote_average,
            vote_count = it.vote_count
    )
}