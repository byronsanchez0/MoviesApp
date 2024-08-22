package com.example.moviesapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FavMovie(
    @PrimaryKey @SerializedName("imdbID")
    val id: String,
    @ColumnInfo(name = "title") @SerializedName ("Title")
    val title: String,
    @ColumnInfo(name = "poster") @SerializedName ("Poster")
    val poster: String,
    @ColumnInfo(name = "year") @SerializedName ("Year")
    val year: String,

)
