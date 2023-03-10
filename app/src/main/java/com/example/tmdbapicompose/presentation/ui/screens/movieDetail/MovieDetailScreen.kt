package com.example.tmdbapicompose.presentation.ui.screens.movieDetail

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tmdbapicompose.R
import com.example.tmdbapicompose.data.repository.response.VideoMovieObjResponse
import com.example.tmdbapicompose.domain.models.*
import com.example.tmdbapicompose.presentation.ui.customComposables.CenterCircularProgressBar
import com.example.tmdbapicompose.presentation.ui.customComposables.LottieLoader
import com.example.tmdbapicompose.utils.Resource

@Composable
fun MovieDetailScreen(navController: NavHostController, result: MoviePopularObjEntity) {
    val viewModel = hiltViewModel<MovieDetailScreenViewModel>()

    viewModel.fetchAllData(result.id, 1)
    viewModel.fetchVideoMovie(result.id)

    val movieState = viewModel.movieRes.value
    val videoMovieState = viewModel.videoMovieRes.collectAsState()


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        result.let {
            val url = "https://image.tmdb.org/t/p/w342/${it.poster_path}"
            Column {
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
                                    text = it.release_date!!,
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
                        text = "Trailer",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 10.dp)
                    )
                    LoadVideo(videoMovieState.value)
                    Text(
                        text = "Synopsis",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 10.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = it.overview, color = Color.Black, fontSize = 14.sp, textAlign = TextAlign.Justify)
                    Text(
                        text = "Review",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 10.dp)
                    )
                    Row(
                        modifier = Modifier.height(240.dp)
                    ) {
                        LoadStateLayout(movieState, viewModel, result.id)
                        Spacer(modifier = Modifier.width(10.dp).fillMaxHeight())
                    }

                }

            }

        }
    }
}



@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoadVideo(
    videoMovieState: Resource<VideoMovieEntity>,
) {

    when (videoMovieState) {
        is Resource.Success -> {
            val videoList : List<VideoMovieObjEntity>? = videoMovieState.data?.results;
            var videoUrl = "404"
            if(videoList?.isNotEmpty() == true) {
                videoUrl = videoList[0].key.toString()
            }
                Row(
                    modifier = Modifier.height(240.dp)
                ) {
                    AndroidView(
                        factory = {
                            WebView(it).apply {
                                settings.javaScriptEnabled = true
                                webViewClient = WebViewClient()
                                webChromeClient = WebChromeClient()
                            }
                        },
                        update = {
                            it.loadUrl("https://www.youtube.com/watch?v=${videoUrl}")
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                    Spacer(modifier = Modifier.width(10.dp).fillMaxHeight())

            }
        }
        is Resource.Loading -> {
            CenterCircularProgressBar()
            LottieLoader(R.raw.loading)
        }
        is Resource.Error -> {
            Toast.makeText(LocalContext.current,"failed", Toast.LENGTH_SHORT).show()
        }

    }

}


@Composable
fun LoadStateLayout(
    movieState: List<ReviewObjEntity>,
    vm: MovieDetailScreenViewModel,
    movieId: Int,

    ) {
                LazyColumn  {
                    itemsIndexed(movieState) { index, it ->
                        Text(text = it.author, color = Color.Blue, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 0.dp))
                        Text(text = "${it.author_details.rating}/10", color = Color.Black, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 0.dp))
                        Text(text = it.content, color = Color.Black, maxLines = 2, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 0.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        LaunchedEffect(key1 = Unit){
                            if (index == movieState.lastIndex) {
                                vm.lastIndex.value = vm.lastIndex.value!! + 1
                                if(vm.lastIndex.value!! > 1){
                                    vm.page.value = vm.page.value!! + 1
                                    vm.fetchAllData(movieId, vm.page.value!!)
                                    vm.lastIndex.value = 0
                                }
                            }
                        }
                    }
                }



}