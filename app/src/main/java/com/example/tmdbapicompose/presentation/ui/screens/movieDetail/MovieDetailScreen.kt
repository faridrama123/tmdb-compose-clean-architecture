package com.example.tmdbapicompose.presentation.ui.screens.movieDetail

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.tmdbapicompose.R
import com.example.tmdbapicompose.data.Resource
import com.example.tmdbapicompose.domain.models.MovieResponse
import com.example.tmdbapicompose.domain.models.Result
import com.example.tmdbapicompose.domain.models.ReviewResponse
import com.example.tmdbapicompose.domain.models.ReviewResponseList
import com.example.tmdbapicompose.domain.utils.Logger
import com.example.tmdbapicompose.domain.utils.popNavigate
import com.example.tmdbapicompose.presentation.ui.customComposables.LottieLoader
import com.example.tmdbapicompose.presentation.ui.screens.home.HomeScreenViewModel
import com.example.tmdbapicompose.presentation.ui.screens.home.LoadMainContent
import com.example.tmdbapicompose.presentation.ui.screens.home.LoadStateLayout
import com.example.tmdbapicompose.presentation.ui.screens.home.MovieDetailScreenViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MovieDetailScreen(navController: NavHostController, result: Result?) {
    val viewModel = hiltViewModel<MovieDetailScreenViewModel>()
    result?.id?.let { viewModel.fetchAllData(it, 1) };
    val movieState = viewModel.movieRes.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        result?.let {
            val url = "https://image.tmdb.org/t/p/w342/${it.poster_path}"
            Column (

            ){
                            Row {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                }
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorScheme.primary)
                        .padding(horizontal = 16.dp, vertical =  30.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .shadow(2.dp, RoundedCornerShape(8.dp))
                    ) {
                        AsyncImage(
                            model = url,
                            contentDescription = it.title,
                            modifier = Modifier.size(140.dp, 230.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = it.title, color = colorScheme.onTertiary, fontSize = 18.sp)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 10.dp)
                        ) {
                            Row {
                                Text(
                                    text = "Language :  ",
                                    color = colorScheme.onTertiary,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = it.original_language,
                                    color = colorScheme.onTertiary,
                                    fontSize = 14.sp
                                )
                            }

                            Row {
                                Text(
                                    text = "Star Rating ",
                                    color = colorScheme.onTertiary,
                                    fontSize = 12.sp
                                )
                                Text(text = it.vote_average.toString(), color = colorScheme.onTertiary, fontSize = 14.sp)
                            }

                            Row {
                                Text(
                                    text = "Release Date :  ",
                                    color = colorScheme.onTertiary,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = it.release_date,
                                    color = colorScheme.onTertiary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }


                Column(
                    modifier = Modifier
                        .background(colorScheme.onPrimary)
                        .padding(20.dp)
                            .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = "Synopsis",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 10.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = it.overview, color = Color.Black, fontSize = 14.sp)
                    Text(
                        text = "Review",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 10.dp)
                    )
                    LoadStateLayout(movieState.value);
                }



            }
        }
    }
}

@Composable
fun display(    navController: NavHostController
) {
    MovieDetailScreen(navController, null)
}

@Composable
fun LoadStateLayout(
    movieState: Resource<ReviewResponse>,
) {

    when (movieState) {
        is Resource.Success -> {
                movieState.value.results.forEach { it ->

                    Text(text = "${it.author}", color = Color.Blue, fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 0.dp))
                    Text(text = "${it.author_details.rating }/10", color = Color.Black, fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 0.dp))
                    Text(text = "${it.content}", color = Color.Black, maxLines = 2, fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 0.dp))
                    Spacer(modifier = Modifier.height(20.dp))

                }
        }
        is Resource.Loading -> {
            LottieLoader(R.raw.loading)
        }
        is Resource.Failure -> {
            Toast.makeText(LocalContext.current,"failed", Toast.LENGTH_SHORT).show()
        }
        else -> {

        }
    }

}
