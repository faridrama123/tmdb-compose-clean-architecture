package com.example.tmdbapicompose.data.repository


import com.example.tmdbapicompose.data.apiCall.SafeApiCall
import com.example.tmdbapicompose.data.apiCall.UserApi
import javax.inject.Inject

/**
 * Repository class to redirect request to datasource eg. Retrofit api &/ Room db
 */

class DetailScreenRepository @Inject constructor(private val api: UserApi): SafeApiCall {

    suspend fun getMovieReview(movieId: Int, page: Int) = safeApiCall {
        api.getMovieReview(movieId, page)
    }
    suspend fun getVideoMovie(movieId: Int) = safeApiCall {
        api.getVideoMovie(movieId)
    }
}