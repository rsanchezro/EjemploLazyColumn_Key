package com.example.ejemplolazycolumn

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ejemplolazycolumn.ui.theme.EjemploLazyColumnTheme
import kotlinx.coroutines.channels.ticker
import kotlin.collections.remove
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme{
                Scaffold(){ padding->LazyColumnActivoDemo(Modifier.padding(padding)) }

            }
        }
    }
}
@Composable
fun LazyColumnActivoDemo(modificador: Modifier=Modifier) {
    // Estado de la LazyColumn
    val listState = rememberLazyListState()
    val listaDatos = remember { List(100) { "Elemento $it" } }

    // Estado para mostrar en pantalla el número de elementos activos
    val visibleCount by remember {
        //Solamente cuando cambia ese valor se recompone la UI

        derivedStateOf { listState.layoutInfo.visibleItemsInfo.size }
    }

    Column(modifier = modificador.fillMaxSize()) {
        // Mostramos el número de elementos activos
        Text(
            text = "Elementos activos en memoria: $visibleCount",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            items(listaDatos) { item ->
                ElementoFila(item)
            }
        }
    }
}

@Composable
fun ElementoFila(texto: String) {
    // Log para ver cuándo se compone la fila
    println("Componiendo $texto")
    Log.i("INFO","Componiendo $texto")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(16.dp)
        )
    }
}