package com.example.nikeshoestorecomposenew.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nikeshoestorecomposenew.R
import com.example.nikeshoestorecomposenew.ui.navigation.AppDestinations
import com.example.nikeshoestorecomposenew.ui.screen.cart.Cart
import com.example.nikeshoestorecomposenew.ui.screen.home.Home
import com.example.nikeshoestorecomposenew.ui.screen.profile.Profile
import com.example.nikeshoestorecomposenew.ui.theme.NikeShoeStoreComposeNewTheme

@Composable
fun MainPage() {
    val currentScreen = remember {
        mutableStateOf(AppDestinations.home)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentScreen.value == AppDestinations.home,
                    onClick = {
                        currentScreen.value = AppDestinations.home
                    },
                    label = { Text(text = stringResource(id = R.string.home)) },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_home_24),
                            contentDescription = ""
                        )
                    },
                )
                NavigationBarItem(
                    selected = currentScreen.value == AppDestinations.cart,
                    onClick = {
                        currentScreen.value = AppDestinations.cart
                    },
                    label = { Text(text = stringResource(id = R.string.cart)) },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_local_mall_24),
                            contentDescription = ""
                        )
                    },
                )
                NavigationBarItem(
                    selected = currentScreen.value == AppDestinations.profile,
                    onClick = {
                        currentScreen.value = AppDestinations.profile
                    },
                    label = { Text(text = stringResource(id = R.string.profile)) },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_person_24),
                            contentDescription = ""
                        )
                    },
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            when (currentScreen.value) {
                AppDestinations.home -> Home()
                AppDestinations.cart -> Cart()
                AppDestinations.profile -> Profile()
            }
        }
    }
}

@Preview
@Composable
fun Default() {
    NikeShoeStoreComposeNewTheme {
        MainPage()
    }
}

@Preview(locale = "fa")
@Composable
fun DefaultFa() {
    NikeShoeStoreComposeNewTheme {
        MainPage()
    }
}