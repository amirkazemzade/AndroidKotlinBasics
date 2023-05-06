package com.example.nikeshoestorecomposenew.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nikeshoestorecomposenew.ui.screen.auth.Auth
import com.example.nikeshoestorecomposenew.ui.screen.profile.Profile

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = AppDestinations.auth) {
        composable(AppDestinations.auth) { Auth(navHostController) }
        composable(AppDestinations.profile) { Profile(navHostController) }
    }
}

class AppDestinations {
    companion object {
        const val auth = "auth"
        const val profile = "profile"
    }
}