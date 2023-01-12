package com.example.tmdbapicompose.data.repository


import com.example.tmdbapicompose.data.apiCall.SafeApiCall
import com.example.tmdbapicompose.data.apiCall.UserApi
import javax.inject.Inject

/**
 * Repository class to redirect request to datasource eg. Retrofit api &/ Room db
 */

class HomeScreenRepository @Inject constructor(private val api: UserApi): SafeApiCall {

    suspend fun getMovieList(genre: String, page : Int) = safeApiCall {
        api.getMovieList(genre, page)
    }
    suspend fun getGenreMovie() = safeApiCall {
        api.getGenreMovie()
    }
}