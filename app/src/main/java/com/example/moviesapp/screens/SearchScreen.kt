package com.example.moviesapp.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.moviesapp.R
import com.example.moviesapp.models.Movie
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MovieSearchScreen(moviesViewModel: MoviesViewModel) {
    val movies by moviesViewModel.movies.collectAsState()
    val loading by moviesViewModel.loading.collectAsState()
    var searchTerm by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchTerm,
            onValueChange = { txt ->
                searchTerm = txt
            },
            label = { Text(text = stringResource(id = R.string.enter_movie_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Button(
            onClick = {
                moviesViewModel.getMovies(searchTerm)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.search_txt),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inverseSurface
            )
        }
        if (loading)
            CircularProgressIndicator(
                color = Color.Blue,
            )
        movieList(movies = movies, viewModel = moviesViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun movieList(
    movies: List<Movie>,
    viewModel: MoviesViewModel
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(movies) { movie ->
            MovieItem(movie, viewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun MovieItem(
    movie: Movie,
    viewModel: MoviesViewModel
) {
    val favBtn by viewModel.favBtn.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val customFont = FontFamily(
        Font(R.font.simplymonobold, FontWeight.Normal)
    )
    Details(sheetState = sheetState, movie, onDismiss = {
        scope.launch {
            sheetState.hide()
        }
    }
    )

    Row(modifier = Modifier
        .padding(16.dp)
        .clickable {
            scope.launch {
                sheetState.partialExpand()
            }
        }) {
        Image(
            painter = rememberAsyncImagePainter(movie.poster),
            contentDescription = "Movie Poster",
            modifier = Modifier.size(80.dp)
        )

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                fontFamily = customFont,
                text = movie.title,
                color = MaterialTheme.colorScheme.inverseSurface,
                maxLines = 3
            )
        }
        IconButton(onClick = { viewModel.addToFavMovie(movie, getUserId(context)) }) {
            Icon(
                favBtn,
                contentDescription = stringResource(R.string.start_playlist),
                modifier = Modifier
                    .size(50.dp),
                tint = MaterialTheme.colorScheme.inversePrimary
            )
        }

    }
}

private fun getUserId(context: Context): Long {
    val sharedPreferences = context.getSharedPreferences("sp", Context.MODE_PRIVATE)
    return sharedPreferences.getInt("userId", 0).toLong()
}
