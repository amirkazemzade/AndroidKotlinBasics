package com.example.nikeshoestorecomposenew.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nikeshoestorecomposenew.ui.screen.auth.Auth
import com.example.nikeshoestorecomposenew.ui.screen.comments.Comments
import com.example.nikeshoestorecomposenew.ui.screen.main_page.MainPage
import com.example.nikeshoestorecomposenew.ui.screen.profile.Profile
import com.example.nikeshoestorecomposenew.ui.screen.shoe.Shoe

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = AppDestinations.auth) {
        composable(AppDestinations.auth) { Auth(navHostController) }
        composable(AppDestinations.profile) { Profile() }
        composable(AppDestinations.mainPage) {
            MainPage(
                navController = navHostController
            )
        }
        composable(
            "${AppDestinations.shoe}/?{id}?{title}?{image}?{price}?{previous_price}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                },
                navArgument("title") {
                    type = NavType.StringType
                },
                navArgument("image") {
                    type = NavType.StringType
                },
                navArgument("price") {
                    type = NavType.IntType
                },
                navArgument("previous_price") {
                    type = NavType.IntType
                },
            )
        ) {
            Shoe(
                id = it.arguments!!.getInt("id"),
                title = it.arguments!!.getString("title")!!,
                image = it.arguments!!.getString("image")!!,
                price = it.arguments!!.getInt("price"),
                previousPrice = it.arguments!!.getInt("previous_price"),
                navController = navHostController,
            )
        }
        composable(
            "${AppDestinations.comments}/?{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            Comments(
                id = it.arguments!!.getInt("id"),
                navController = navHostController
            )
        }
    }
}

class AppDestinations {
    companion object {
        const val auth = "auth"
        const val profile = "profile"
        const val home = "home"
        const val cart = "cart"
        const val mainPage = "mainPage"
        const val shoe = "shoe"
        const val comments = "comments"
    }
}