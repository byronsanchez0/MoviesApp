package com.example.moviesapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.sharp.Dangerous
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.entity.FavMovie
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.RetrofitCient
import com.example.moviesapp.repository.FavRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(private val retrofitClient: RetrofitCient, private val favRepo: FavRepo): ViewModel() {

    private val listMovie = MutableStateFlow<List<Movie>>(listOf())
    val movies: StateFlow<List<Movie>> get() = listMovie

    private val favBtnStateFlow = MutableStateFlow<ImageVector>(Icons.Filled.FavoriteBorder)
    val favBtn: MutableStateFlow<ImageVector> get() = favBtnStateFlow

    private val loadingFlow = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> get() = loadingFlow
    fun getMovies(movieQuery: String) {
        viewModelScope.launch {
            loadingFlow.emit(true)
            val result = retrofitClient.searchMovies(movieQuery)
            listMovie.emit(result)
            loadingFlow.emit(false)
        }
    }


    fun addToFavMovie(movie: Movie, userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val favMovie = FavMovie(
                movie.id,
                movie.title,
                movie.poster,
                movie.year,
                userId
            )
            favRepo.addFavMovie(favMovie)
        }
    }
}