package com.example.moviesapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.moviesapp.database.FavDataBase
import com.example.moviesapp.models.RetrofitCient
import com.example.moviesapp.navigation.MainScreen
import com.example.moviesapp.repository.FavRepo
import com.example.moviesapp.screens.MoviesViewModel
import com.example.moviesapp.screens.favorites.FavoritesViewModel
import com.example.moviesapp.screens.login.LoginScreen
import com.example.moviesapp.screens.login.LoginViewModel
import com.example.moviesapp.screens.signup.SignUp
import com.example.moviesapp.screens.signup.SignUpViewModel
import com.example.moviesapp.ui.theme.MoviesAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = this.getSharedPreferences("sp", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("userId", 0)

        val db = Room.databaseBuilder(
            this,
            FavDataBase::class.java, FavDataBase.DATABASE_NAME
        ).build()
        val favMoviesRepo = FavRepo(db.favMoviesDao())
        var startDestination = "login"
        if (id != 0) {
            startDestination = "mainscreen"
        }
        val context = this
        setContent {
            val favoritesViewModel = FavoritesViewModel(favMoviesRepo)
            val moviesViewModel = MoviesViewModel(RetrofitCient(), favMoviesRepo)
            MoviesAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = startDestination) {
                    composable("login") {
                        val viewModel = LoginViewModel(context)
                        LoginScreen(navController, viewModel)
                    }
                    composable("signup") {
                        val viewModel = SignUpViewModel(context)
                        SignUp(navController, viewModel)
                    }
                    composable("mainscreen") {
                        MainScreen(navController, favoritesViewModel, moviesViewModel)
                    }
                }
            }
        }
    }
}
