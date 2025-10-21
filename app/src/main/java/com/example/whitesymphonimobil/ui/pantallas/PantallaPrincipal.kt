﻿package com.example.whitesymphonymobil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.whitesymphonymobil.R
import com.example.whitesymphonymobil.data.model.Producto
import kotlin.math.roundToInt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    onIrCarrito: () -> Unit,
    onAgregar: (Producto) -> Unit
) {
    val productos = listOf(
        Producto("Musica 1", 45000.0, R.drawable.logo, 4.5),
        Producto("Musica 2", 60000.0, R.drawable.logo, 4.0),
        Producto("Musica 3", 120000.0, R.drawable.logo, 5.0)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo White Symphony",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("White Symphony", style = MaterialTheme.typography.titleLarge)
                    }
                },
                actions = {
                    IconButton(onClick = onIrCarrito) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(productos) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(id = producto.imageRes),
                            contentDescription = producto.nombre,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        Spacer(modifier = Modifier.width(16.dp))


                        Column(modifier = Modifier.weight(1f)) {
                            Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                            Text("Precio: $${producto.precio}")
                            RatingStars(producto.rating)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { onAgregar(producto) },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Agregar")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RatingStars(rating: Double) {
    val rounded = rating.roundToInt()
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rounded) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
