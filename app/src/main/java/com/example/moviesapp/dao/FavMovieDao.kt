package com.example.moviesapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.entity.FavMovie

@Dao
interface FavMovieDao {
    @Query("SELECT * FROM FavMovie")
    fun getAll(): List<FavMovie>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg favmovies: FavMovie)
    @Delete
    fun delete(favMovie: FavMovie)
}