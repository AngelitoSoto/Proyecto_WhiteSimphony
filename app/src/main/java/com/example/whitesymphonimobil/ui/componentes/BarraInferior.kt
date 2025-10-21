package com.example.whitesymphonymobil.ui.componentes

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable

@Composable
fun BarraInferior(
    onHomeClick: () -> Unit,
    onUserClick: () -> Unit,
    currentScreen: String
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentScreen == "home",
            onClick = onHomeClick,
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = currentScreen == "user",
            onClick = onUserClick,
            icon = { Icon(Icons.Filled.Person, contentDescription = "Usuario") },
            label = { Text("Usuario") }
        )
    }
}