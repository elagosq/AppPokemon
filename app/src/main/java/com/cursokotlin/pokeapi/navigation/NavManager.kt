package com.cursokotlin.pokeapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cursokotlin.pokeapi.views.home.DetailView
import com.cursokotlin.pokeapi.views.home.FavoriteView
import com.cursokotlin.pokeapi.views.home.HomeView
import com.cursokotlin.pokeapi.views.home.SearchView
import com.cursokotlin.pokeapi.views.home.UserView
import com.cursokotlin.pokeapi.views.login.TabsViewLogin


@Composable
fun NavManager() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "HomeView") {
        composable("LoginView") {
            TabsViewLogin(navController)
        }

        composable("HomeView") {
            HomeView(navController)
        }

        composable(
            "DetailView/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) {
            val name = it.arguments?.getString("name") ?: ""
            DetailView(navController,name)
        }

        composable("UserView") {
            UserView(navController)
        }


        composable("SearchView") {
            SearchView(navController)
        }


        composable("FavoriteView") {
            FavoriteView(navController)
        }

    }
}