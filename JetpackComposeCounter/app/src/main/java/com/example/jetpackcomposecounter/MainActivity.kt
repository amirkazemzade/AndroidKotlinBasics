package com.example.jetpackcomposecounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposecounter.ui.theme.JetpackComposeCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeCounterTheme {
                Counter()
            }
        }
    }
}

@Composable
fun Counter() {
    val count = remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar() {
                Text(text = "Counter Compose", style = MaterialTheme.typography.h6)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                count.value += 1
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "You have tapped:")
            Box(modifier = Modifier.height(8.dp))
            Text(text = "${count.value}", style = MaterialTheme.typography.h3)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeCounterTheme {
        Counter()
    }
}