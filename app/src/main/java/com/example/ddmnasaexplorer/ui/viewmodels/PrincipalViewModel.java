package com.example.ddmnasaexplorer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddmnasaexplorer.data.api.NasaApi
import com.example.ddmnasaexplorer.data.models.ApodResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

// 1. Define os possíveis estados da nossa tela
sealed interface ApodUiState {
    object Loading : ApodUiState
    data class Success(val apod: ApodResponse) : ApodUiState
    data class Error(val message: String) : ApodUiState
}

        // 2. ViewModel principal
        class PrincipalViewModel : ViewModel() {

    // StateFlow privado (modificável apenas dentro do ViewModel)
    private val _uiState = MutableStateFlow<ApodUiState>(ApodUiState.Loading)

            // StateFlow público (somente leitura pela UI)
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
                // Chamada da API — use sua API Key ou "DEMO_KEY"
                val response = NasaApi.retrofitService.getPictureOfTheDay(apiKey = "DEMO_KEY")
                _uiState.value = ApodUiState.Success(response)
            } catch (e: IOException) {
                // Erro de rede (sem internet, etc.)
                _uiState.value = ApodUiState.Error("Erro de conexão. Verifique sua internet.")
            } catch (e: Exception) {
                // Outros erros (parsing, timeout, etc.)
                _uiState.value = ApodUiState.Error("Erro ao buscar dados: ${e.message}")
            }
        }
    }
}
