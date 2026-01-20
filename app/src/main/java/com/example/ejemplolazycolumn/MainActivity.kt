package com.example.ejemplolazycolumn

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlin.collections.remove
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EjemploLazyColumnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Lista_elementos(Modifier.padding(innerPadding))
                }
            }
        }
    }
}
data class Elemento(var indice:Int)
@Composable
fun Lista_elementos(modificador: Modifier= Modifier)
{
    var lista_datos by remember { mutableStateOf(List(100){indice->Elemento(indice)}) }
    LazyColumn(modifier = modificador) {
  //Al ponerle un key si borro un elemento no aparece el checked otra vez
        //prueba a quitar el key, comprobarás que al eliminar el elemento queda checkeado
   itemsIndexed(items=lista_datos, key = {ind,elemento->elemento.indice}){indice,elemento->
       Row() {
           //Se invoca tantas veces a Elemento_lista como elementos visibles
           Elemento_lista(indice, elemento){
               //Click en Borrar
                //Una forma de borrar, creando otra lista
                 lista_datos = lista_datos.filter { it.indice!=elemento.indice }
                 //Otra forma de borrar el elemento clickado
                 /*
                lista_datos=lista_datos.toMutableList().apply {
                    //La función apply recibe el objeto MutableList como parametro
                    //y retorna un objeto MutableList
                    remove(elemento)
                }*/

           }


       }
   }

    }
}

/*
Esta función se ejecuta solamente para los elementos visibles,
se creara una variable chequeado por cada elemento visible
si hacemos scroll se destruye ese elemento y se crea uno nuevo con lo que se inicia a false

 */
@Composable
fun Elemento_lista(indice:Int,elemento: Elemento,click_elemento:()->Unit)
{
    Log.i("INFO","Elemento lista $indice")

    var chequeado by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 4.dp)){
        Spacer(Modifier.padding(start = 4.dp))
        Text(text = "Indice Lazy:$indice", fontSize = 10.sp, modifier = Modifier.weight(1.5f))

        Text(text="Elemento ${elemento.indice}", fontSize = 10.sp, modifier = Modifier.weight(1.5f))


        Checkbox(checked = chequeado, onCheckedChange = { chequeado=it }, modifier = Modifier.weight(1f))

        Button(onClick = click_elemento, modifier = Modifier.weight(2f)

        ) {
            Text("Borrar")
        }


    }
}