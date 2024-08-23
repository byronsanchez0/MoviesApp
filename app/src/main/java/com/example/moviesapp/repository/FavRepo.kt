package com.example.moviesapp.repository

import com.example.moviesapp.dao.FavMovieDao
import com.example.moviesapp.entity.FavMovie


class FavRepo(private val favMovieDao: FavMovieDao) {
    fun addFavMovie(favMovie: FavMovie){
        favMovieDao.insertAll(favMovie)
    }
    fun getFavsMovies(): List<FavMovie>{
        return favMovieDao.getAll()
    }
    fun  deleteFavMovie(favMovie: FavMovie){
        favMovieDao.delete(favMovie)
    }
}