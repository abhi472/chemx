package com.tmr.chemx2

import android.annotation.SuppressLint
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tmr.chemx2.navigation.Screens
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {

    val auth = Firebase.auth

    val isAuthenticated by remember(auth.currentUser) {
        derivedStateOf {
            auth.currentUser != null
        }
    }

    if (!isAuthenticated) {
        navController.popBackStack()
        navController.navigate(Screens.Login.route)
    }

    val bottomBarNavController = rememberNavController()

    Scaffold(bottomBar = {
        BottomAppBar { BottomNavigationBar(navController = bottomBarNavController) }
    }) { NavigationScreens(navController = bottomBarNavController) }
}