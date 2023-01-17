package com.example.tmdbapicompose.presentation.ui.screens.movieDetail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbapicompose.domain.models.*
import com.example.tmdbapicompose.domain.usecase.UseCases
import com.example.tmdbapicompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailScreenViewModel
@Inject constructor(
    private val useCases: UseCases,
)
    : ViewModel() {


    private val _movieRes = mutableStateOf<List<ReviewObjEntity>>(emptyList())
    val movieRes: State<List<ReviewObjEntity>> = _movieRes


    private val _videoMovieRes = MutableStateFlow<Resource<VideoMovieEntity>>(Resource.Loading()
    )
    var videoMovieRes: StateFlow<Resource<VideoMovieEntity>> = _videoMovieRes.asStateFlow()


    val page: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(1)
    }
    val lastIndex: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }


    fun <T> merge(first: List<T>, second: List<T>): List<T> {
        return first + second
    }

    fun fetchAllData(id: Int, page: Int) {
        viewModelScope.launch {
            when(val result = useCases.getReviewMovieUseCase(id, page)){
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

    fun fetchVideoMovie(id: Int) {
        viewModelScope.launch {
            _videoMovieRes.value =
                useCases.getVideoUseCase(id)
        }
    }

}