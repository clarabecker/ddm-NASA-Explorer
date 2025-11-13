package com.example.ddmnasaexplorer.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ddmnasaexplorer.data.models.ApodResponse
import com.example.ddmnasaexplorer.viewmodels.ApodUiState
import com.example.ddmnasaexplorer.viewmodels.PrincipalViewModel
import android.net.Uri
import androidx.compose.foundation.border

import androidx.compose.ui.graphics.Color
import com.example.ddmnasaexplorer.ui.theme.NasaBlue

@Composable
fun PrincipalScreen(
    navController: NavController,
    viewModel: PrincipalViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is ApodUiState.Loading -> LoadingState()
        is ApodUiState.Error -> ErrorState(message = (uiState as ApodUiState.Error).message)
        is ApodUiState.Success -> {
            SuccessState(
                apodData = (uiState as ApodUiState.Success).apod,
                navController = navController
            )
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
fun SuccessState(apodData: ApodResponse, navController: NavController) {
    val context = LocalContext.current

    // Cor da borda (use a do seu tema ou defina aqui)
    val borderColor = Color(0xFF0B3D91)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        // Caixa com Título "Foto do dia"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, borderColor, RoundedCornerShape(12.dp))
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Foto do dia",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Caixa com a Imagem
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Ocupa o espaço disponível
                .border(2.dp, borderColor, RoundedCornerShape(12.dp))
                .padding(16.dp), // Espaço entre a borda e a foto
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(apodData.url)
                    .crossfade(true)
                    .build(),
                contentDescription = apodData.title,
                contentScale = ContentScale.Crop, // Ou Fit se quiser ver inteira
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)) // Arredonda a foto levemente
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botão "Ver detalhes"
        Button(
            onClick = {
                // Codifica os textos para passar na URL (evita erros com espaços e acentos)
                val encodedTitle = Uri.encode(apodData.title)
                val encodedDesc = Uri.encode(apodData.explanation)
                val encodedUrl = Uri.encode(apodData.url)

                // Navega passando os dados
                navController.navigate("detalhes/$encodedTitle/$encodedDesc/$encodedUrl")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = borderColor)
        ) {
            Text(text = "Ver detalhes", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
