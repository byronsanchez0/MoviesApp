package com.example.moviesapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviesapp.MovieSearchScreen
//import com.example.moviesapp.MovieSearchScreen
import com.example.moviesapp.screens.favorites.FavoritesScreen
import com.example.moviesapp.screens.favorites.FavoritesViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BottomNavGraph(navHostController: NavHostController, favoritesViewModel: FavoritesViewModel) {

    NavHost(
        navController = navHostController,
        startDestination = "moviesearchscreen"
    ){
        composable("moviesearchscreen"){
            MovieSearchScreen()
        }
        composable(route = "favorites"){
            FavoritesScreen(favoritesViewModel)
        }
    }
}