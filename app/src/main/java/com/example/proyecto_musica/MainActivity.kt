package com.example.proyecto_musica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_musica.ui.theme.ProyectoMusicaTheme
import kotlin.math.roundToInt
import android.graphics.Bitmap
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import android.app.Activity
import androidx.compose.ui.graphics.asImageBitmap
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.delay
import android.Manifest
import androidx.compose.ui.text.input.PasswordVisualTransformation

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
    val rating: Double
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoMusicaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScreen()
                }
            }
        }
    }
}

@Composable
fun AppScreen() {
    val cartItems = remember { mutableStateListOf<Product>() }
    var currentScreen by remember { mutableStateOf("home") }

    Scaffold(
        topBar = { Banner(onCartClick = { currentScreen = "cart" }) },
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = { currentScreen = "home" },
                onUserClick = { currentScreen = "user" },
                onCartClick = { currentScreen = "cart" }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                "home" -> ProductList(
                    products = sampleProducts(),
                    onAddToCart = { cartItems.add(it) }
                )

                "user" -> UserScreen(
                    onLoginSuccess = {
                        currentScreen = "home"
                    }
                )
                "cart" -> CartScreen(
                    cartItems = cartItems,
                    onBack = { currentScreen = "home" }
                )
            }
        }
    }
}

data class LoginResult(
    val emailError: String? = null,
    val passwordError: String? = null,
    val isValid: Boolean = false
)

object LoginValidator {
    private val emailPattern = Regex("^.+@.+\\..+\$")

    fun validate(email: String, password: String): LoginResult {
        var emailErr: String? = null
        var passErr: String? = null

        if (!emailPattern.matches(email)) {
            emailErr = "Correo inválido, debe contener '@' y un dominio"
        }

        if (password.length !in 6..12) {
            passErr = "Contraseña debe tener entre 6 y 12 caracteres"
        }

        return LoginResult(
            emailError = emailErr,
            passwordError = passErr,
            isValid = emailErr == null && passErr == null
        )
    }
}

@Composable
fun UserScreen(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginResult by remember { mutableStateOf(LoginResult()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Iniciar Sesión", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            isError = loginResult.emailError != null,
            modifier = Modifier.fillMaxWidth()
        )
        loginResult.emailError?.let {
            Text(it, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            isError = loginResult.passwordError != null,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        loginResult.passwordError?.let {
            Text(it, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                loginResult = LoginValidator.validate(email, password)
                if (loginResult.isValid) {
                    onLoginSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }
    }
}

@Composable
fun Banner(onCartClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "White Symphony",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onCartClick) {
            Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
        }
    }
}

@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit,
    onUserClick: () -> Unit,
    onCartClick: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            selected = false,
            onClick = onHomeClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Usuario") },
            selected = false,
            onClick = onUserClick
        )

    }
}

@Composable
fun ProductList(products: List<Product>, onAddToCart: (Product) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(products) { product ->
            ProductCard(product = product, onAddToCart = onAddToCart)
        }
    }
}

@Composable
fun ProductCard(product: Product, onAddToCart: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold)
                Text("$${product.price}", color = MaterialTheme.colorScheme.primary)
                RatingStars(product.rating)
            }
            Button(onClick = { onAddToCart(product) }) {
                Text("Agregar")
            }
        }
    }
}

@Composable
fun CartScreen(
    cartItems: MutableList<Product>,
    onBack: () -> Unit
) {
    var mostrandoPago by remember { mutableStateOf(false) }
    var mostrandoReseña by remember { mutableStateOf(false) }
    var pagoFinalizado by remember { mutableStateOf(false) }

    when {
        mostrandoPago -> {
            PantallaPago {
                mostrandoPago = false
                mostrandoReseña = true
                cartItems.clear()
            }
        }

        mostrandoReseña -> {
            ReviewScreen(
                products = sampleProducts(),
                onFinish = {
                    mostrandoReseña = false
                    pagoFinalizado = true
                }
            )
        }

        pagoFinalizado -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("¡Reseña Publicada!", fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = onBack) { Text("Volver") }
                }
            }
        }

        else -> {
            val total = cartItems.sumOf { it.price }

            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = onBack) { Text("Volver") }
                    Text("Carrito", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, fontSize = 20.sp)
                }

                if (cartItems.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Tu carrito está vacío 🛒")
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(cartItems) { product ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = product.imageRes),
                                    contentDescription = product.name,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(product.name, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                    Text("$${product.price}")
                                }
                                Button(
                                    onClick = { cartItems.remove(product) },
                                    modifier = Modifier.height(40.dp)
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
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { mostrandoPago = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Pagar")
                        }
                    }
                }
            }
        }
    }
}





@Composable
fun PantallaPago(onPagoFinalizado: () -> Unit) {
    var procesando by remember { mutableStateOf(true) }
    val context = LocalContext.current


    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                mostrarNotificacion(
                    context,
                    titulo = "Pago exitoso",
                    mensaje = "Tu pago se ha realizado correctamente ✅"
                )
            }
        }


    LaunchedEffect(Unit) {
        delay(3000)
        procesando = false


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            mostrarNotificacion(
                context,
                titulo = "Pago exitoso",
                mensaje = "Tu pago se ha realizado correctamente ✅"
            )
        }

        delay(2000)
        onPagoFinalizado()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (procesando) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Procesando pago...", fontSize = 18.sp)
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Pago realizado",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "¡Pago realizado con éxito!",
                    fontSize = 20.sp,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}

fun mostrarNotificacion(context: Context, titulo: String, mensaje: String) {
    val channelId = "pago_channel"
    val notificationId = 1001

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Canal de pago"
        val descriptionText = "Notificaciones cuando se realiza un pago"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    try {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}

@Composable
fun ReviewScreen(products: List<Product>, onFinish: () -> Unit) {
    var reviewText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0.0) }
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current


    LaunchedEffect(Unit) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(android.Manifest.permission.CAMERA),
            0
        )
    }


    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            capturedImage = bitmap
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Deja tu reseña", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        RatingStars(rating)
        Slider(
            value = rating.toFloat(),
            onValueChange = { rating = it.toDouble() },
            valueRange = 0f..5f,
            steps = 4
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = reviewText,
            onValueChange = { reviewText = it },
            label = { Text("Escribe tu reseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { cameraLauncher.launch(null) }) {
            Text("Agregar foto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la imagen capturada
        capturedImage?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onFinish, modifier = Modifier.fillMaxWidth()) {
            Text("Enviar reseña")
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

fun sampleProducts(): List<Product> = listOf(
    Product(1, "Musica 1", 29990.0, R.drawable.logo, 4.5),
    Product(2, "Musica 2", 49990.0, R.drawable.logo, 4.0),
    Product(3, "Musica 3", 59990.0, R.drawable.logo, 5.0)
)
