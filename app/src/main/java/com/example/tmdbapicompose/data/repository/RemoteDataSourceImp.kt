package com.example.tmdbapicompose.data.repository


import com.example.tmdbapicompose.data.apiCall.UserApi
import com.example.tmdbapicompose.domain.models.GenreMovieEntity
import com.example.tmdbapicompose.domain.models.MoviePopularEntity
import com.example.tmdbapicompose.domain.models.ReviewEntity
import com.example.tmdbapicompose.domain.models.VideoMovieEntity
import com.example.tmdbapicompose.domain.repository.IRemoteDataSource
import com.example.tmdbapicompose.domain.utils.DataMapper
import com.example.tmdbapicompose.utils.Resource
import javax.inject.Inject


class RemoteDataSourceImp @Inject constructor(
    private val api: UserApi
): IRemoteDataSource {
    override suspend fun getMovieList(genre: String, page: Int): Resource<MoviePopularEntity> {
        val response = try {
            api.getMovieList2(genre, page)
        }catch (e:Exception){
            return Resource.Error("Unknown Error")
        }
        val mapResponse = DataMapper.mapMoviePopular(response)
        return Resource.Success(mapResponse)
    }
    override suspend fun getReview(id: Int, page: Int): Resource<ReviewEntity> {
        val response = try {
            api.getMovieReview(id, page)
        }catch (e:Exception){
            return Resource.Error("Unknown Error")
        }
        val mapResponse = DataMapper.mapReviewMovie(response)
        return Resource.Success(mapResponse)
    }

    override suspend fun getGenre(): Resource<GenreMovieEntity> {
        val response = try {
            api.getGenreMovie()
        }catch (e:Exception){
            return Resource.Error("Unknown Error")
        }
        val mapResponse = DataMapper.mapGenre(response)
        return Resource.Success(mapResponse)
    }

    override suspend fun getVideo(id: Int): Resource<VideoMovieEntity> {
        val response = try {
            api.getVideoMovie(id)
        }catch (e:Exception){
            return Resource.Error("Unknown Error")
        }
        val mapResponse = DataMapper.mapVideo(response)
        return Resource.Success(mapResponse)
    }


}