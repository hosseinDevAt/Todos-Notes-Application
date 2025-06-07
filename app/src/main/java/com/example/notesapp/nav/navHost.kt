package com.example.notesapp.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.screens.HomeScreen
import com.example.notesapp.screens.RecycleScreen

@Composable
fun mainScreen(){
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "home"){

        composable("home") {
            HomeScreen(onGoToRecycle = {navController.navigate("recycle")})
        }

        composable("recycle") {
            RecycleScreen(onBack = {navController.popBackStack()})
        }


    }

}