package com.example.tmdbapicompose.presentation.ui.screens.movieDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbapicompose.domain.models.*
import com.example.tmdbapicompose.domain.usecase.UseCases
import com.example.tmdbapicompose.domain.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel      //Mandatory for Hilt to Inject ViewModel
class MovieDetailScreenViewModel
@Inject constructor( // Hilt constructor injection
    private val useCases: UseCases,
) //Constructor Injection
    : ViewModel() {


    private val _movieRes = MutableStateFlow<com.example.tmdbapicompose.utils.Resource<ReviewEntity>>(
        com.example.tmdbapicompose.utils.Resource.Loading()
    )
    var movieRes: StateFlow<com.example.tmdbapicompose.utils.Resource<ReviewEntity>> = _movieRes.asStateFlow()



    private val _videoMovieRes = MutableStateFlow<com.example.tmdbapicompose.utils.Resource<VideoMovieEntity>>(        com.example.tmdbapicompose.utils.Resource.Loading()
    )
    var videoMovieRes: StateFlow<com.example.tmdbapicompose.utils.Resource<VideoMovieEntity>> = _videoMovieRes.asStateFlow()

    val cacheReview: MutableLiveData<List<ReviewObjEntity>> by lazy {
        MutableLiveData<List<ReviewObjEntity>>()
    }

    val page: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(1)
    }
    val lastIndex: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }


    fun fetchAllData(id: Int, page: Int) {
        viewModelScope.launch {
            _movieRes.value =
                useCases.getReviewMovieUseCase(id, page)
        }
    }
    fun fetchVideoMovie(id: Int) {
        viewModelScope.launch {
            _videoMovieRes.value =
                useCases.getVideoUseCase(id)
        }
    }

}