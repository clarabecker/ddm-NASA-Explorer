package com.example.ddmnasaexplorer.data.room


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteApod(
    @PrimaryKey val url: String,
    val title: String,
    val explanation: String,
    val mediaType: String,
    val hdUrl: String?
)
