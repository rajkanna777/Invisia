package com.example.invisia.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val movies: List<Movie>
)


