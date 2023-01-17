package com.example.tmdbapicompose.presentation.ui.screens.home


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbapicompose.domain.models.GenreMovieEntity
import com.example.tmdbapicompose.domain.models.MoviePopularObjEntity
import com.example.tmdbapicompose.domain.usecase.UseCases
import com.example.tmdbapicompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel
@Inject constructor(
    private val useCases: UseCases,
    )
    : ViewModel() {

    private val _movieRes = mutableStateOf<List<MoviePopularObjEntity>>(emptyList())
    val movieRes: State<List<MoviePopularObjEntity>> = _movieRes


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

    private fun <T> merge(first: List<T>, second: List<T>): List<T> {
        return first + second
    }
    fun fetchAllData(genre: String, page: Int) {
        viewModelScope.launch {
            when(val result = useCases.getMoviePopularUseCase(genre, page)){
                is Resource.Success -> {
                    if(page == 1){
                        _movieRes.value = result.data?.results!!
                    }else {
                        _movieRes.value = merge(_movieRes.value, result.data?.results!!)
                    }
                    Log.d("size of",  _movieRes.value.size.toString())
                }is Resource.Error ->{
            }else ->{}
            }

        }
    }

    private fun fetchAllGenre() {
        viewModelScope.launch {
            _genreRes.value =
                useCases.getGenrUseCase()
        }
    }


}