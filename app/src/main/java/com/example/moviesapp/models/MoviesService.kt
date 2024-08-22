package com.example.moviesapp.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("/")
    suspend fun searchMovies(
        @Query("apiKey") apikey: String,
        @Query("s") searchTerm: String

    ): Response<MovieSearchResponse>
}