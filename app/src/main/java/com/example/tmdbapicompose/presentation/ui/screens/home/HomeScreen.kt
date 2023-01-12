package com.example.tmdbapicompose.presentation.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tmdbapicompose.R
import com.example.tmdbapicompose.data.Resource
import com.example.tmdbapicompose.domain.models.GenreMovie
import com.example.tmdbapicompose.domain.models.GenreMovieResponse
import com.example.tmdbapicompose.domain.models.MovieResponse
import com.example.tmdbapicompose.domain.models.Result
import com.example.tmdbapicompose.domain.utils.Logger
import com.example.tmdbapicompose.domain.utils.superNavigate
import com.example.tmdbapicompose.presentation.navigation.Screen
import com.example.tmdbapicompose.presentation.ui.customComposables.CenterCircularProgressBar
import com.example.tmdbapicompose.presentation.ui.customComposables.LottieLoader
import com.example.tmdbapicompose.presentation.ui.theme.Typography
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.lifecycle.HiltViewModel

const val url = "https://image.tmdb.org/t/p/w342"

@Composable
fun HomeScreen(navController: NavHostController, logger: Logger) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val movieState = viewModel.movieRes.collectAsState()
    val genreState = viewModel.genreRes.collectAsState()
    val genreState_: Resource<GenreMovieResponse> = genreState.value;

    when(genreState_){
        is Resource.Success -> {
            val genreList : List<GenreMovie> = genreState_.value.genres;

            Column() {
                Text(
                    text = "Lewancon",
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                LazyRow() {
                    items(items = genreList, itemContent = { item ->
                        Button(
                            modifier = (Modifier.padding(5.dp)),
                            onClick = {  viewModel.fetchAllData(item.id.toString(), 1) },
                            // Uses ButtonDefaults.ContentPadding by default
                            contentPadding = PaddingValues(
                                start = 20.dp,
                                top = 12.dp,
                                end = 20.dp,
                                bottom = 12.dp
                            )
                        ) {
                            // Inner content including an icon and a text label
                            Text(item.name)
                        }
                    })
                }
                var swipeRefreshState = rememberSwipeRefreshState(isRefreshing = true)


                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.fetchAllData("all", 1) }) {
                        LoadStateLayout(
                            movieState = movieState.value,
                            genreState = genreState.value,
                            navController = navController,
                            viewModel = viewModel,
                            logger = logger
                        ){
                            swipeRefreshState.isRefreshing = !it
                        }
                    }
                }

            }

        }
        else -> {}
    }
}


@Composable
fun LoadStateLayout(
    movieState: Resource<MovieResponse>,
    genreState: Resource<GenreMovieResponse>,
    navController: NavHostController,
    viewModel: HomeScreenViewModel,
    logger: Logger,
    onLoad:(loaded:Boolean)->Unit
) {

    when (movieState) {
        is Resource.Success -> {
            onLoad(true)
            when(genreState){
                is Resource.Success -> {
                    LoadMainContent(
                        movieList = movieState.value.results,
                        genreList = genreState.value.genres,
                        navController = navController,
                        viewModel = viewModel,
                    )
                }
                else -> {}
            }
        }
        is Resource.Loading -> {
            LottieLoader(R.raw.loading)
            onLoad(false)
        }
        is Resource.Failure -> {
            onLoad(false)
            Toast.makeText(LocalContext.current,"failed",Toast.LENGTH_SHORT).show()
        }
        else -> {

        }
    }

}

@Composable
fun LoadMainContent(
    movieList: List<Result>,
    genreList: List<GenreMovie>,
    navController: NavHostController,
    viewModel: HomeScreenViewModel,
    ) {


    Column(Modifier.fillMaxSize()) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(125.dp),
                content = {
                    itemsIndexed(movieList) { index, item ->
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            ItemView(item) {
                                navController.apply {
                                    currentBackStackEntry?.savedStateHandle?.set(
                                        key = "result",
                                        value = item
                                    )

                                    superNavigate(Screen.MovieDetails.route)
                                }

                                //navController.navigate("details/$title/$release_date$poster_path/$original_language/${vote_average.toString()}/$overview")
                            }
                        }
                    }
                }
            )
        }
}

@Composable
fun ItemView(result: Result, onItemClicked: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .fillMaxSize()
            .clickable { onItemClicked() }
    ) {
        val showProgressBarState = remember { mutableStateOf(false) }
        if (showProgressBarState.value) {
            CenterCircularProgressBar()
        }
        AsyncImage(
            onLoading = {
                showProgressBarState.value = true
            },
            onSuccess = {
                showProgressBarState.value = false
            },
            model = url + result.poster_path,
            alignment = Alignment.Center,
            contentDescription = result.title,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}
