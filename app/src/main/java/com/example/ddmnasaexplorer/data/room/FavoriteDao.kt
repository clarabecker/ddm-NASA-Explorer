package com.example.ddmnasaexplorer.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    fun getFavorites(): LiveData<List<FavoriteApod>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteApod)

    @Delete
    suspend fun delete(favorite: FavoriteApod)

    @Query("SELECT * FROM favorites WHERE url = :url LIMIT 1")
    suspend fun getByUrl(url: String): FavoriteApod?
}
