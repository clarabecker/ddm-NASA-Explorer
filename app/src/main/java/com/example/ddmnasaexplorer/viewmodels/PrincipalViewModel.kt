package com.example.ddmnasaexplorer.viewmodels

import com.example.ddmnasaexplorer.data.api.NasaApi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddmnasaexplorer.data.models.ApodResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

// 1. Define os possíveis estados da tela
sealed interface ApodUiState {
    object Loading : ApodUiState
    data class Success(val apod: ApodResponse) : ApodUiState
    data class Error(val message: String) : ApodUiState
}

// 2. ViewModel principal
class PrincipalViewModel : ViewModel() {

    // StateFlow privado (mutável apenas dentro do ViewModel)
    private val _uiState = MutableStateFlow<ApodUiState>(ApodUiState.Loading)

    // StateFlow público (somente leitura para a UI)
    val uiState: StateFlow<ApodUiState> = _uiState.asStateFlow()

    // 3. Inicializa buscando a foto do dia
    init {
        fetchPictureOfTheDay()
    }

    // 4. Função que busca os dados da API da NASA
    private fun fetchPictureOfTheDay() {
        viewModelScope.launch {
            _uiState.value = ApodUiState.Loading // Estado de carregamento
            try {
                val response = NasaApi.retrofitService.getPictureOfTheDay(apiKey = "egMRVVd9RHdKrHdYO5srZvr1aAamLCtYQgLkQe8l")
                _uiState.value = ApodUiState.Success(response)
            } catch (e: IOException) {
                _uiState.value = ApodUiState.Error("Erro de conexão. Verifique sua internet.")
            } catch (e: Exception) {
                _uiState.value = ApodUiState.Error("Erro ao buscar dados: ${e.message}")
            }
        }
    }
}
