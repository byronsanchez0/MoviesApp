package com.example.moviesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp.dao.FavMovieDao
import com.example.moviesapp.entity.FavMovie

@Database(entities = [FavMovie::class], version = 1)
abstract class FavDataBase: RoomDatabase(){
    companion object {
        const val DATABASE_NAME = "favdatabase"
    }

    abstract fun favMoviesDao(): FavMovieDao
}