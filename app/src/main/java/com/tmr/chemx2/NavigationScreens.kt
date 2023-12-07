package com.tmr.chemx2

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tmr.chemx2.navigation.homeNav.NavItem
import com.tmr.chemx2.tabs.HomeScreen
import com.tmr.chemx2.tabs.ListScreen
import com.tmr.chemx2.tabs.ProfileScreen
import com.tmr.chemx2.tabs.SearchScreen

@Composable
fun NavigationScreens(navController: NavHostController) {
    NavHost(navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) { HomeScreen() }
        composable(NavItem.Search.path) { SearchScreen() }
        composable(NavItem.List.path) { ListScreen() }
        composable(NavItem.Profile.path) { ProfileScreen() }
    }
}