package com.example.ddmnasaexplorer.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ddmnasaexplorer.data.room.FavoriteApod

@Database(entities = [FavoriteApod::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoriteDao
}
