package com.example.ddmnasaexplorer.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ddmnasaexplorer.data.models.ApodResponse
import com.example.ddmnasaexplorer.viewmodels.ApodUiState
import com.example.ddmnasaexplorer.viewmodels.PrincipalViewModel

@Composable
fun PrincipalScreen(
    // 1. Pega a instância do ViewModel
    viewModel: PrincipalViewModel = viewModel()
) {
    // 2. Observa o uiState. A tela vai recompor sempre que ele mudar.
    val uiState by viewModel.uiState.collectAsState()

    // 3. Usa um 'when' para decidir o que mostrar
    when (uiState) {
        is ApodUiState.Loading -> {
            LoadingState() // Mostra o "rodando"
        }
        is ApodUiState.Success -> {
            // Deu certo, mostra os dados
            val apodData = (uiState as ApodUiState.Success).apod
            SuccessState(apodData = apodData)
        }
        is ApodUiState.Error -> {
            // Deu erro, mostra a mensagem
            val message = (uiState as ApodUiState.Error).message
            ErrorState(message = message)
        }
    }
}

// Composable para o estado de Carregando
@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

// Composable para o estado de Erro
@Composable
fun ErrorState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
    }
}

// Composable para o estado de Sucesso (baseado no seu protótipo)
@Composable
fun SuccessState(apodData: ApodResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Permite rolar a tela
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Foto do dia",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 4. Usa o Coil (AsyncImage) para carregar a imagem da URL
        if (apodData.mediaType == "image") {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(apodData.url) // URL da imagem
                    .crossfade(true)
                    .build(),
                contentDescription = apodData.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f) // Proporção
                    .clip(RoundedCornerShape(12.dp))
            )
        } else {
            // Se for um vídeo, apenas mostramos um aviso
            Text("Mídia do dia é um vídeo (não suportado ainda).")
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = apodData.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = apodData.explanation,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* TODO: Navegar para detalhes */ }) {
            Text(text = "Ver detalhes")
        }
    }
}