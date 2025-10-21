package com.example.whitesymphonymobil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.whitesymphonymobil.data.model.Producto
import com.example.whitesymphonymobil.ui.pantallas.*
import com.example.whitesymphonymobil.ui.componentes.BarraInferior
import com.example.whitesymphonymobil.ui.theme.WhiteSymphonyTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.example.whitesymphonymobil.ui.screens.PantallaUsuario
import com.example.whitesymphonymobil.ui.screens.PantallaPrincipal
import com.example.whitesymphonymobil.ui.screens.PantallaCarrito
import com.example.whitesymphonymobil.ui.screens.PantallaResena

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhiteSymphonyTheme {
                var pantallaActual by remember { mutableStateOf("home") }
                val carrito = remember { mutableStateListOf<Producto>() }

                Scaffold(
                    bottomBar = {

                        if (pantallaActual in listOf("home", "usuario")) {
                            BarraInferior(
                                onHomeClick = { pantallaActual = "home" },
                                onUserClick = { pantallaActual = "usuario" },
                                currentScreen = pantallaActual
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (pantallaActual) {

                            "home" -> PantallaPrincipal(
                                onIrCarrito = { pantallaActual = "carrito" },
                                onAgregar = { carrito.add(it) }
                            )


                            "carrito" -> PantallaCarrito(
                                carrito = carrito,
                                onVolver = { pantallaActual = "home" }
                            )


                            "pago" -> PantallaPago {
                                pantallaActual = "resena"
                            }


                            "resena" -> PantallaResena(
                                productos = carrito,
                                onFinish = { pantallaActual = "usuario" }
                            )


                            "usuario" -> PantallaUsuario(
                                onLoginSuccess = { pantallaActual = "home" }
                            )
                        }
                    }
                }
            }
        }
    }
}


