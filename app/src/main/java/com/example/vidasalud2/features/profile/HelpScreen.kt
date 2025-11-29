package com.example.vidasalud2.features.profile // O en una carpeta 'features/help'

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Modelo de datos para una FAQ
private data class FaqItem(
    val question: String,
    val answer: String
)

// Lista de FAQs de ejemplo
private val faqs = listOf(
    FaqItem("¿Cómo registro mis comidas?", "Ve a la pestaña 'Registro', selecciona la pestaña 'Alimentación', rellena los campos de 'Artículo' y 'Calorías', y presiona el botón 'Guardar'."),
    FaqItem("¿Mis datos están seguros?", "Sí, nos tomamos la privacidad muy en serio. Todos tus datos están encriptados y almacenados de forma segura."),
    FaqItem("¿Cómo cancelo mi suscripción?", "Puedes gestionar tu suscripción en cualquier momento desde 'Perfil' > 'Mi Suscripción'."),
    FaqItem("¿La app se sincroniza con mi smartwatch?", "¡Sí! Ve a 'Perfil' > 'Conexión de Datos Externos' para conectar Google Fit o Samsung Health.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ayuda y Soporte") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    text = "Preguntas Frecuentes",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // Creamos una tarjeta expandible por cada FAQ
            items(faqs) { faq ->
                FaqItemCard(faq = faq)
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /* TODO: Abrir cliente de email o chat */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Contactar con Soporte")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// Composable de ayuda para la tarjeta expandible (Acordeón)
@Composable
private fun FaqItemCard(faq: FaqItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .animateContentSize() // Anima la expansión
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = faq.question,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f) // Ocupa el espacio
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Cerrar" else "Expandir"
                )
            }

            if (expanded) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                Text(
                    text = faq.answer,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}