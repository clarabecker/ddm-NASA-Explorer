package com.example.ddmnasaexplorer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*

// Importe suas telas
import com.example.ddmnasaexplorer.ui.screens.DetalhesScreen
import com.example.ddmnasaexplorer.ui.screens.FavoritosScreen
import com.example.ddmnasaexplorer.ui.screens.GaleriaScreen
import com.example.ddmnasaexplorer.ui.screens.PrincipalScreen

// 1. Definição das rotas para a barra de navegação
sealed class NavScreen(val route: String, val label: String, val icon: ImageVector) {
    object Principal : NavScreen("principal", "Principal", Icons.Default.Home)
    object Galeria : NavScreen("galeria", "Galeria", Icons.Default.Search)
    object Favoritos : NavScreen("favoritos", "Favoritos", Icons.Default.Favorite)
}

// Lista das telas da barra inferior
private val bottomNavItems = listOf(
    NavScreen.Principal,
    NavScreen.Galeria,
    NavScreen.Favoritos,
)

// 2. Composable principal da Navegação
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
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
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // 3. O Host que controla a troca de telas
        NavHost(
            navController = navController,
            startDestination = NavScreen.Principal.route, // Tela inicial
            modifier = Modifier.padding(innerPadding)
        ) {
            // Tela Principal
            composable(NavScreen.Principal.route) {
                PrincipalScreen()
            }
            // Tela Galeria
            composable(NavScreen.Galeria.route) {
                GaleriaScreen()
                // Futuramente: onClick = { navController.navigate("detalhes") }
            }
            // Tela Favoritos
            composable(NavScreen.Favoritos.route) {
                FavoritosScreen()
                // Futuramente: onClick = { navController.navigate("detalhes") }
            }

            // Tela Detalhes (não está na barra inferior)
            composable("detalhes") {
                DetalhesScreen()
            }
        }
    }
}