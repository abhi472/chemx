package com.tmr.chemx2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tmr.chemx2.navigation.Screens

@Composable
fun HomeScreen(navController: NavController) {
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

    Text(text = "Hello ${auth.currentUser}")
}