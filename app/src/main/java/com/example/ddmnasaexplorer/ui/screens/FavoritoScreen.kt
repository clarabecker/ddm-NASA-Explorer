package com.example.ddmnasaexplorer.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.livedata.observeAsState  // <--- importante
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ddmnasaexplorer.data.room.FavoriteApod
import com.example.ddmnasaexplorer.viewmodels.FavoritesViewModel

@Composable
fun FavoritoScreen(viewModel: FavoritesViewModel = hiltViewModel() // <-- aqui Hilt injeta o ViewModel
) {
    val favorites by viewModel.favorites.observeAsState(emptyList())

    if (favorites.isEmpty()) {
        Text("Nenhum favorito salvo")
        return
    }

    LazyColumn {
        items(favorites) { item ->
            Text(item.title)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(favorites) { apod ->
            FavoriteItem(apod = apod, onToggle = { viewModel.toggleFavorite(it) })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FavoriteItem(apod: FavoriteApod, onToggle: (FavoriteApod) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberAsyncImagePainter(apod.url),
            contentDescription = apod.title,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 16.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = apod.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = apod.explanation, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
        }
        IconButton(onClick = { onToggle(apod) }) {
            Icon(
                painter = rememberAsyncImagePainter("ic_favorite"), // ícone local
                contentDescription = "Remover favorito"
            )
        }
    }
}
