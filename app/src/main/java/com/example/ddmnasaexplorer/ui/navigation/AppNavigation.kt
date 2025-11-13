package com.example.ddmnasaexplorer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.example.ddmnasaexplorer.ui.screens.DetalhesScreen
import com.example.ddmnasaexplorer.ui.screens.FavoritosScreen
import com.example.ddmnasaexplorer.ui.screens.GaleriaScreen
import com.example.ddmnasaexplorer.ui.screens.PrincipalScreen

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.fillMaxSize


// 1. Definição das rotas para a barra de navegação
sealed class NavScreen(val route: String, val label: String, val icon: ImageVector) {
    object Principal : NavScreen("principal", "Principal", Icons.Default.Home)
    object Galeria : NavScreen("galeria", "Galeria", Icons.Default.Photo)
    object Favoritos : NavScreen("favoritos", "Favoritos", Icons.Default.Favorite)
}

// Lista das telas da barra inferior
private val bottomNavItems = listOf(
    NavScreen.Principal,
    NavScreen.Galeria,
    NavScreen.Favoritos,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val darkBackgroundColor = Color(0xFF0A0A1A)

    Scaffold(
        containerColor = darkBackgroundColor,
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("https://gpm.nasa.gov/sites/default/files/2021-11/nasa-logo.png")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Logo NASA",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(end = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkBackgroundColor,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar (containerColor = Color(0xFF1C2130)){
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                   // saveState = true
                                }
                                launchSingleTop = true
                                //restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavScreen.Principal.route,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable(NavScreen.Principal.route) {
                PrincipalScreen(navController = navController)
            }
            composable(NavScreen.Galeria.route) {
                GaleriaScreen()
            }
            composable(NavScreen.Favoritos.route) {
                FavoritosScreen()
            }
            composable(
                route = "detalhes/{title}/{description}/{url}",
                arguments = listOf(
                    navArgument("title") { type = NavType.StringType },
                    navArgument("description") { type = NavType.StringType },
                    navArgument("url") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val description = backStackEntry.arguments?.getString("description") ?: ""
                val url = backStackEntry.arguments?.getString("url") ?: ""
                DetalhesScreen(title = title, description = description, url = url)
            }
        }
    }
}
