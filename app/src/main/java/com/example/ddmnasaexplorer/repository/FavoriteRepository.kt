package com.example.ddmnasaexplorer.repository

import androidx.lifecycle.LiveData
import com.example.ddmnasaexplorer.data.room.FavoriteApod
import com.example.ddmnasaexplorer.data.room.FavoriteDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    private val dao: FavoriteDao
) {
    val favorites: LiveData<List<FavoriteApod>> = dao.getFavorites()

    suspend fun addFavorite(apod: FavoriteApod) {
        dao.insert(apod)
    }

    suspend fun removeFavorite(apod: FavoriteApod) {
        dao.delete(apod)
    }

    suspend fun getFavoriteByUrl(url: String): FavoriteApod? {
        return dao.getByUrl(url)
    }
}
