package com.example.tmdbapicompose.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbapicompose.data.Resource
import com.example.tmdbapicompose.data.repository.HomeScreenRepository
import com.example.tmdbapicompose.domain.models.GenreMovieResponse
import com.example.tmdbapicompose.domain.models.MovieResponse
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
class HomeScreenViewModel
@Inject constructor( // Hilt constructor injection
    private val repository : HomeScreenRepository, // injecting HomeScreenRepository provided by AppModule
    private val logger:Logger // Injecting Independent Logger.class
    ) //Constructor Injection
    : ViewModel() {

    private val _movieRes = MutableStateFlow<Resource<MovieResponse>>(Resource.Initial)
    var movieRes: StateFlow<Resource<MovieResponse>> = _movieRes.asStateFlow()

    private val _genreRes = MutableStateFlow<Resource<GenreMovieResponse>>(Resource.Initial)
    var genreRes: StateFlow<Resource<GenreMovieResponse>> = _genreRes.asStateFlow()

    init {
        fetchAllGenre()
        fetchAllData("all", 1)
    }

    fun fetchAllData(genre: String, page: Int) {
        logger.i("Test............")
        viewModelScope.launch {
            try {
                _movieRes.update { Resource.Loading }
                val res1 = async { repository.getMovieList(genre, page) }
                val resultFromApi1 = res1.await()
                _movieRes.update { resultFromApi1 }
            } catch (exception: Exception) {
                logger.e("LogException", exception)
            }
        }
    }
    fun fetchAllGenre() {
        logger.i("Test Genre............")
        viewModelScope.launch {
            try {
                _genreRes.update { Resource.Loading }
                val res1 = async { repository.getGenreMovie() }
                val resultFromApi1 = res1.await()
                _genreRes.update { resultFromApi1 }
            } catch (exception: Exception) {
                logger.e("LogException", exception)
            }
        }
    }
}