package com.example.tmdbapicompose.presentation.ui.screens.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbapicompose.domain.models.GenreMovieEntity
import com.example.tmdbapicompose.domain.models.MoviePopularEntity
import com.example.tmdbapicompose.domain.usecase.UseCases
import com.example.tmdbapicompose.domain.utils.Logger
import com.example.tmdbapicompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel      //Mandatory for Hilt to Inject ViewModel
class HomeScreenViewModel
@Inject constructor( // Hilt constructor injection
    private val useCases: UseCases,
    ) //Constructor Injection
    : ViewModel() {

    private val _movieRes = MutableStateFlow<Resource<MoviePopularEntity>>(Resource.Loading())
    var movieRes: StateFlow<Resource<MoviePopularEntity>> = _movieRes.asStateFlow()


    private val _genreRes = MutableStateFlow<Resource<GenreMovieEntity>>(Resource.Loading())
    var genreRes: StateFlow<Resource<GenreMovieEntity>> = _genreRes.asStateFlow()

    // Create a LiveData with a String
    val movieId: MutableLiveData<String> by lazy {
        MutableLiveData<String>("all")
    }
    val page: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(1)
    }
    val lastIndex: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }

    init {
        fetchAllGenre()
        fetchAllData("all", 1)
    }


    fun fetchAllData(genre: String, page: Int) {
        viewModelScope.launch {
            _movieRes.value =
                useCases.getMoviePopularUseCase(genre, page)
            }
    }

    fun fetchAllGenre() {
        viewModelScope.launch {
            _genreRes.value =
                useCases.getGenrUseCase()
        }
    }


}