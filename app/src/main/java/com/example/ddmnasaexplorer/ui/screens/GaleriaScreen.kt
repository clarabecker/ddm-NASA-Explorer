package com.example.ddmnasaexplorer.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ddmnasaexplorer.data.models.ApodResponse
import com.example.ddmnasaexplorer.viewmodels.GaleriaUiState
import com.example.ddmnasaexplorer.viewmodels.GaleriaViewModel
import androidx.compose.foundation.lazy.rememberLazyListState

@Composable
fun GaleriaScreen(
    navController: NavController? = null,
    viewModel: GaleriaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // 1. Estado para controlar a posição da lista
    val listState = rememberLazyListState()

    // 2. Lógica para detectar o fim da lista (Scroll Infinito)
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    // 3. Chama o ViewModel quando chegar no fim
    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            viewModel.fetchGaleria() // Carrega mais 10!
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // ... (Título e cabeçalho igual antes) ...

        when (uiState) {
            is GaleriaUiState.Loading -> {
                // Loading inicial
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF0B3D91))
                }
            }
            is GaleriaUiState.Error -> {
                Text(text = (uiState as GaleriaUiState.Error).message, color = Color.Red)
            }
            is GaleriaUiState.Success -> {
                val photos = (uiState as GaleriaUiState.Success).photos

                LazyColumn(
                    state = listState, // <-- IMPORTANTE: Passar o estado aqui
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(photos) { photo ->
                        GaleriaItemCard(photo = photo, navController = navController)
                    }

                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GaleriaItemCard(photo: ApodResponse, navController: NavController?) {
    // Estado local para controlar o coração (favorito) visualmente
    var isFavorite by remember { mutableStateOf(false) }
    val borderColor = Color(0xFF0B3D91)

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C2130)), // Fundo do Card
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
    ) {
        Column {
            // Imagem do Card
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.url)
                    .crossfade(true)
                    .build(),
                contentDescription = photo.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp) // Altura fixa para ficar uniforme
            )

            Column(modifier = Modifier.padding(12.dp)) {
                // Título da Imagem
                Text(
                    text = photo.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Linha com Botão e Coração
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Botão Ver Detalhes
                    Button(
                        onClick = {
                            val encodedTitle = Uri.encode(photo.title)
                            val encodedDesc = Uri.encode(photo.explanation)
                            val encodedUrl = Uri.encode(photo.url)
                            navController?.navigate("detalhes/$encodedTitle/$encodedDesc/$encodedUrl")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        modifier = Modifier.border(1.dp, borderColor, RoundedCornerShape(8.dp)),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("Ver detalhes", color = Color.White, style = MaterialTheme.typography.bodySmall)
                    }

                    // Ícone de Favorito (Coração)
                    IconButton(onClick = {
                        isFavorite = !isFavorite
                        // AQUI: Futuramente vamos salvar no Banco de Dados Room
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favoritar",
                            tint = Color(0xFFFC3D21)
                        )
                    }
                }
            }
        }
    }
}