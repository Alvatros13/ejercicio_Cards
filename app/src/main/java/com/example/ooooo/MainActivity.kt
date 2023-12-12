package com.example.ooooo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.example.ooooo.ui.theme.OooooTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OooooTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    pantalla()                }
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun pantalla() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        items(20) { index ->
            tarjeta("Tarea $index")
            if (index < 19) {
                MyDivider()
            }
        }
    }
}

@Composable
fun tarjeta(tarea: String) {
    var selectedPriority by rememberSaveable {
        mutableStateOf(R.drawable.low_priority)
    }

    Card(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(Color.Blue),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray,
        ),
    ) {
        Image(
            painter = painterResource(id = selectedPriority),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = tarea,
                color = Color.Black // Mantener el color del texto en negro
            )
            Row(Modifier.fillMaxWidth()) {
                val priority = dropMenuPriprity()
                if (priority == "Media") {
                    selectedPriority = R.drawable.medium_priority
                } else if (priority == "Baja") {
                    selectedPriority = R.drawable.low_priority
                } else if (priority == "Alta") {
                    selectedPriority = R.drawable.high_priority
                }
            }

            Row(Modifier.fillMaxWidth()) {
                dropMenuEstado()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropMenuPriprity(): String{
    var selectedPriority by rememberSaveable {
        mutableStateOf("Baja")
    }
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    val priorities = listOf("Baja", "Media", "Alta")
    val textColor = Color.Black
    Column (Modifier.padding(5.dp)){
        OutlinedTextField(value = selectedPriority, onValueChange = { selectedPriority = it },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .clickable { expanded = true }
                .fillMaxWidth(),
            textStyle = TextStyle(color = textColor)
        )
        DropdownMenu(expanded = expanded,
            onDismissRequest = {expanded = false},
            modifier = Modifier.fillMaxWidth(),) {
            priorities.forEach{
                    priority -> DropdownMenuItem(text = {Text(text=priority)},
                onClick={
                    expanded = false
                    selectedPriority = priority
                })
            }
        }
    }
    return selectedPriority
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropMenuEstado(): String{
    var selectedPriority by rememberSaveable {
        mutableStateOf("No Completada")
    }
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    val priorities = listOf("No Completada", "Completada")
    val textColor = Color.Black
    Column (Modifier.padding(5.dp)){
        OutlinedTextField(value = selectedPriority, onValueChange = { selectedPriority = it },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .clickable { expanded = true }
                .fillMaxWidth(),
            textStyle = TextStyle(color = textColor)

        )
        DropdownMenu(expanded = expanded,
            onDismissRequest = {expanded = false},
            modifier = Modifier.fillMaxWidth()) {
            priorities.forEach{
                    priority -> DropdownMenuItem(text = {Text(text=priority)},
                onClick={
                    expanded = false
                    selectedPriority = priority
                })
            }
        }
    }
    return selectedPriority
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_ExposedDropdownMenuBox() {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("Americano", "Cappuccino", "Espresso", "Latte", "Mocha")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(coffeeDrinks[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                coffeeDrinks.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MyDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    )
}