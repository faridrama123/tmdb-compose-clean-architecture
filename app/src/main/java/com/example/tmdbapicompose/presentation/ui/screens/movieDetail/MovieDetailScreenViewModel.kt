package com.example.tmdbapicompose.presentation.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbapicompose.data.Resource
import com.example.tmdbapicompose.data.repository.DetailScreenRepository
import com.example.tmdbapicompose.data.repository.HomeScreenRepository
import com.example.tmdbapicompose.domain.models.MovieResponse
import com.example.tmdbapicompose.domain.models.ReviewResponse
import com.example.tmdbapicompose.domain.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel      //Mandatory for Hilt to Inject ViewModel
class MovieDetailScreenViewModel
@Inject constructor( // Hilt constructor injection
    private val repository : DetailScreenRepository, // injecting HomeScreenRepository provided by AppModule
    private val logger:Logger // Injecting Independent Logger.class
) //Constructor Injection
    : ViewModel() {

    private val _movieRes = MutableStateFlow<Resource<ReviewResponse>>(Resource.Initial)
    var movieRes: StateFlow<Resource<ReviewResponse>> = _movieRes.asStateFlow()

    // Create a LiveData with a String
    val movieId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }
    init {
    }

    fun fetchAllData(movieId: Int, page: Int) {
        logger.i("Test............")
        viewModelScope.launch {
            try {
                _movieRes.update { Resource.Loading }
                val res1 = async { repository.getMovieReview(movieId) }
                val resultFromApi1 = res1.await()
                _movieRes.update { resultFromApi1 }
            } catch (exception: Exception) {
                logger.e("LogException", exception)
            }
        }
    }
}