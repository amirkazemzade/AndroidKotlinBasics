package com.example.nikeshoestorecomposenew.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nikeshoestorecomposenew.ui.screen.auth.Auth
import com.example.nikeshoestorecomposenew.ui.screen.main_page.MainPage
import com.example.nikeshoestorecomposenew.ui.screen.profile.Profile

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = AppDestinations.auth) {
        composable(AppDestinations.auth) { Auth(navHostController) }
        composable(AppDestinations.profile) { Profile() }
        composable(AppDestinations.mainPage) { MainPage() }
    }
}

class AppDestinations {
    companion object {
        const val auth = "auth"
        const val profile = "profile"
        const val home = "home"
        const val cart = "cart"
        const val mainPage = "mainPage"
    }
}