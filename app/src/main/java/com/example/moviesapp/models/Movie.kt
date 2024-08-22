package com.example.moviesapp.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("imdbID")
    val id: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Year")
    val year: String
)
