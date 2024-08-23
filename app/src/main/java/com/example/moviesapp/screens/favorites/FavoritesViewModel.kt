package com.example.moviesapp.screens.favorites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.sharp.Dangerous
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.entity.FavMovie
import com.example.moviesapp.models.Movie
import com.example.moviesapp.repository.FavRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class FavoritesViewModel(private val favRepo: FavRepo) : ViewModel() {

    private val favBtnStateFlow = MutableStateFlow<ImageVector>(Icons.Filled.FavoriteBorder)
    private val delBtnStateFlow = MutableStateFlow<ImageVector>(Icons.Sharp.Dangerous)
    private val listFavMovie = MutableStateFlow<List<FavMovie>>(listOf())

    val favBtn: MutableStateFlow<ImageVector> get() = favBtnStateFlow
    val detlBtn: MutableStateFlow<ImageVector> get() = delBtnStateFlow
    val movies: StateFlow<List<FavMovie>> get() = listFavMovie

    fun deleteFavMovie(movie: FavMovie){
        viewModelScope.launch(Dispatchers.IO) {
            favRepo.deleteFavMovie(movie)
            getFavMovies(movie.userId)
        }
    }
    fun getFavMovies(userId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            val favcharacters = favRepo.getFavsMovies()
            listFavMovie.emit(favcharacters)
        }
    }
}
