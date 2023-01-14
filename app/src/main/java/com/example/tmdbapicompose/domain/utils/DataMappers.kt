package com.example.tmdbapicompose.domain.utils

import com.example.tmdbapicompose.data.repository.response.*
import com.example.tmdbapicompose.domain.models.*


object DataMapper {


    fun mapMoviePopular(it: MoviePopularResponse) = MoviePopularEntity(
            page =  it.page,
            results = it.results.map {mapMoviePopularObj(it)},
            total_pages = it.total_pages,
            total_results = it.total_results
    )
        fun mapMoviePopularObj(it: MoviePopularObjResponse) = MoviePopularObjEntity(
                adult = it.adult?: false,
                backdrop_path = it.backdrop_path?: "",
                genre_ids = it.genre_ids ,
                id = it.id ,
                original_language = it.original_language?: "",
                original_title = it.original_title,
                overview =  it.overview?: "",
                popularity =  it.popularity?: 0.0,
                poster_path = it.poster_path?: "",
                release_date = it.release_date?: "",
                title = it.title?: "",
                video = it.video?: false,
                vote_average = it.vote_average,
                vote_count = it.vote_count
        )

        fun mapReviewMovie(it: ReviewResponse) = ReviewEntity(
                id = it.id,
                page =  it.page,
                results = it.results.map {mapReviewObjMovie(it)},
                total_pages = it.total_pages,
                total_results = it.total_results
        )
        fun mapReviewObjMovie(it: ReviewObjResponse) = ReviewObjEntity(
                author = it.author,
                author_details = mapAuthor(it.author_details),
                content = it.content,
                created_at = it.created_at,
                id = it.id,
                updated_at = it.updated_at,
                url = it.url
        )
        fun mapAuthor(it: AuthorDetailsResponse) = AuthorDetailsEntity(
                name= it.name,
                username = it.username,
                avatar_path= it.avatar_path?: "",
                rating= it.rating?: 0.0,
        )

    fun mapGenre(it: GenreMovieResponse) = GenreMovieEntity(
        genres= it.genres.map { mapGenreObj(it) }

    )
    fun mapGenreObj(it: GenreMovieObjResponse) = GenreMovieObjEntity(
        id = it.id,
        name = it.name
    )

    fun mapVideo(it: VideoMovieResponse) = VideoMovieEntity(
        id = it.id,
        results = it.results.map { mapVideoObj(it) },
    )
    fun mapVideoObj(it: VideoMovieObjResponse) = VideoMovieObjEntity(
        iso639_1 = it.iso639_1,
        iso3166_1= it.iso639_1,
        name = it.name,
        key= it.key,// String?,
        site= it.site,// String?,
        size= it.size,// Long?,
        type= it.type,// String?,
        official= it.official,// Boolean?,
        publishedAt= it.publishedAt,// String?,
        id= it.id
    )
}