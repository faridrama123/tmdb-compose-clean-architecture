package com.example.tmdbapicompose.presentation.ui.screens.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tmdbapicompose.R
import com.example.tmdbapicompose.domain.models.GenreMovieEntity
import com.example.tmdbapicompose.domain.models.GenreMovieObjEntity

import com.example.tmdbapicompose.domain.models.MoviePopularEntity
import com.example.tmdbapicompose.domain.models.MoviePopularObjEntity
import com.example.tmdbapicompose.domain.utils.Logger
import com.example.tmdbapicompose.domain.utils.superNavigate
import com.example.tmdbapicompose.presentation.navigation.Screen
import com.example.tmdbapicompose.presentation.ui.customComposables.CenterCircularProgressBar
import com.example.tmdbapicompose.presentation.ui.customComposables.LottieLoader
import com.example.tmdbapicompose.presentation.ui.theme.Typography
import com.example.tmdbapicompose.utils.Resource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

const val url = "https://image.tmdb.org/t/p/w342"

@Composable
fun HomeScreen(navController: NavHostController, logger: Logger) {
    logger.i("Home Screen............")

    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val movieState = viewModel.movieRes
    val genreState = viewModel.genreRes.collectAsState()
    when(val genreStateData: Resource<GenreMovieEntity> = genreState.value){
        is Resource.Success -> {
            val genreList : List<GenreMovieObjEntity> = genreStateData.data?.genres!!

            Column {
                Text(
                    text = "MDB Movie",
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                LazyRow {
                    items(items = genreList, itemContent = { item ->
                        Button(
                            modifier = (Modifier.padding(5.dp)),
                            onClick = {
                                viewModel.movieId.value = item.id.toString()
                                viewModel.fetchAllData(viewModel.movieId.value!!, 1)
                                      },
                            // Uses ButtonDefaults.ContentPadding by default
                            contentPadding = PaddingValues(
                                start = 20.dp,
                                top = 12.dp,
                                end = 20.dp,
                                bottom = 12.dp
                            )
                        ) {
                            // Inner content including an icon and a text label
                            Text(item.name!!)
                        }
                    })
                }
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = true)


                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.movieId.value?.let {
                        viewModel.page.value = 1
                        viewModel.fetchAllData(it, 1)
                    } }) {
                        LoadStateLayout(
                            movieState = movieState.value,
                            navController = navController,
                            viewModel = viewModel,
                        )
                        {
                            swipeRefreshState.isRefreshing = !it
                        }
                    }
                }

            }

        }
        is Resource.Loading -> {
            LottieLoader(R.raw.loading)
        }
        is Resource.Error -> {
            Toast.makeText(LocalContext.current,"failed",Toast.LENGTH_SHORT).show()
        }

    }
}


@Composable
fun LoadStateLayout(
    movieState: List<MoviePopularObjEntity>,
    navController: NavHostController,
    viewModel: HomeScreenViewModel,
    onLoad:(loaded:Boolean)->Unit
) {

    LoadMainContent(
        movieList = movieState,
        navController = navController,
        vm = viewModel,
    )
    onLoad(true)
}

@Composable
fun LoadMainContent(
    movieList: List<MoviePopularObjEntity>,
    navController: NavHostController,
    vm: HomeScreenViewModel,
    ) {


    Log.d("data movieList", movieList.toString())
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

                            }
                        }
                        LaunchedEffect(key1 = Unit){
                            if (index == movieList.lastIndex) {
                                vm.lastIndex.value = vm.lastIndex.value!! + 1
                                if(vm.lastIndex.value!! > 1){
                                    vm.page.value = vm.page.value!! + 1
                                    vm.fetchAllData(vm.movieId.value!!, vm.page.value!!)
                                    vm.lastIndex.value = 0
                                }
                            }
                        }
                    }
                }
            )
        }
}

@Composable
fun ItemView(result: MoviePopularObjEntity, onItemClicked: () -> Unit) {
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
