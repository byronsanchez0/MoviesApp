package com.example.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviesapp.MovieSearchScreen
//import com.example.moviesapp.MovieSearchScreen
import com.example.moviesapp.screens.FavoritesScreen

@Composable
fun BottomNavGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = "moviesearchscreen"
    ){
        composable("moviesearchscreen"){
            MovieSearchScreen()
        }
        composable(route = "favorites"){
            FavoritesScreen()
        }
    }
}