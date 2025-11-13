package com.example.ddmnasaexplorer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddmnasaexplorer.data.api.NasaApi
import com.example.ddmnasaexplorer.data.models.ApodResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estado específico para a Galeria (Lista de fotos)
sealed interface GaleriaUiState {
    object Loading : GaleriaUiState
    data class Success(val photos: List<ApodResponse>) : GaleriaUiState
    data class Error(val message: String) : GaleriaUiState
}

class GaleriaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<GaleriaUiState>(GaleriaUiState.Loading)
    val uiState: StateFlow<GaleriaUiState> = _uiState.asStateFlow()

    // Lista interna para acumular as fotos
    private val _currentPhotos = mutableListOf<ApodResponse>()

    // Controle para não chamar a API várias vezes ao mesmo tempo
    private var isLoadingMore = false

    init {
        fetchGaleria()
    }

    fun fetchGaleria() {
        // Evita chamadas duplicadas se já estiver carregando
        if (isLoadingMore) return

        isLoadingMore = true

        viewModelScope.launch {
            // Se for a primeira vez (lista vazia), mostra Loading
            if (_currentPhotos.isEmpty()) {
                _uiState.value = GaleriaUiState.Loading
            }

            try {
                // Busca mais 10 fotos
                val newPhotos = NasaApi.retrofitService.getGaleria(apiKey = "DEMO_KEY", count = 10)

                // Adiciona as novas fotos à lista acumulada
                _currentPhotos.addAll(newPhotos)

                // Atualiza o estado com a lista COMPLETA
                _uiState.value = GaleriaUiState.Success(_currentPhotos.toList())

            } catch (e: Exception) {
                // Só mostra erro se a lista estiver vazia, senão apenas ignora (ou mostra um Toast/Snackbar)
                if (_currentPhotos.isEmpty()) {
                    _uiState.value = GaleriaUiState.Error("Erro ao carregar galeria: ${e.message}")
                }
            } finally {
                isLoadingMore = false
            }
        }
    }
}