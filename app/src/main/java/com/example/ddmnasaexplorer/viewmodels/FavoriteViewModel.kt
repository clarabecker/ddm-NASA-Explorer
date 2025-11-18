package com.example.ddmnasaexplorer.viewmodels

import com.example.ddmnasaexplorer.data.room.FavoriteApod
import com.example.ddmnasaexplorer.repository.FavoriteRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {

    val favorites: LiveData<List<FavoriteApod>> = repository.favorites

    fun toggleFavorite(apod: FavoriteApod) {
        viewModelScope.launch {
            val existing = repository.getFavoriteByUrl(apod.url)
            if (existing != null) {
                repository.removeFavorite(existing)
            } else {
                repository.addFavorite(apod)
            }
        }
    }
}
