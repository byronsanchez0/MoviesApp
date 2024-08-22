package com.example.moviesapp.models

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("Search")
    val search: List<Movie>,
    val totalResult:Int,
)
