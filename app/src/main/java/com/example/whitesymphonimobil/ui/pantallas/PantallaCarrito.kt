package com.example.whitesymphonymobil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whitesymphonymobil.data.model.Producto
import com.example.whitesymphonymobil.ui.pantallas.PantallaPago


@Composable
fun PantallaCarrito(
    carrito: MutableList<Producto>,
    onVolver: () -> Unit
) {
    var mostrandoPago by remember { mutableStateOf(false) }
    var mostrandoResena by remember { mutableStateOf(false) }
    var pagoFinalizado by remember { mutableStateOf(false) }

    when {

        mostrandoPago -> {
            PantallaPago {
                mostrandoPago = false
                mostrandoResena = true
                carrito.clear()
            }
        }


        mostrandoResena -> {
            PantallaResena(
                productos = carrito,
                onFinish = {
                    mostrandoResena = false
                    pagoFinalizado = true
                }
            )
        }


        pagoFinalizado -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("¡Reseña publicada con éxito!", fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = onVolver) { Text("Volver al inicio") }
                }
            }
        }


        else -> {
            val total = carrito.sumOf { it.precio }

            Column(modifier = Modifier.fillMaxSize()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = onVolver) {
                        Text("Volver al inicio")
                    }
                    Text(
                        "Carrito",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }


                if (carrito.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Tu carrito está vacío 🛒")
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(carrito) { producto ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = producto.imageRes),
                                    contentDescription = producto.nombre,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(producto.nombre, fontWeight = FontWeight.Bold)
                                    Text("$${producto.precio}")
                                }
                                Button(
                                    onClick = { carrito.remove(producto) },
                                    modifier = Modifier.height(40.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
                                ) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Total: $${String.format("%,.0f", total)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { mostrandoPago = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Pagar")
                        }

                        Spacer(modifier = Modifier.height(8.dp))


                        OutlinedButton(
                            onClick = onVolver,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Volver a la página principal")
                        }
                    }
                }
            }
        }
    }
}
