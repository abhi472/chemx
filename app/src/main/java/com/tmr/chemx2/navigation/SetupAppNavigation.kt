package com.tmr.chemx2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tmr.chemx2.AuthScreen
import com.tmr.chemx2.HomeScreen

@Composable
fun SetupAppNavigation(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screens.Login.route) {
            AuthScreen(navController = navController)

        }
    }
}