package com.example.nikeshoestorecomposenew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.nikeshoestorecomposenew.navigation.AppNavHost
import com.example.nikeshoestorecomposenew.ui.theme.NikeShoeStoreComposeNewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NikeShoeStoreComposeNewTheme {
                AppNavHost(navHostController = navController)
            }
        }
    }
}