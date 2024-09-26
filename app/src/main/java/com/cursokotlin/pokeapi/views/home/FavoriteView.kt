package com.cursokotlin.pokeapi.views.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.cursokotlin.pokeapi.components.MainTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteView(navController: NavController) {
    Scaffold(
        topBar = {
            MainTopBar(
                title = "Favorito",
                showBackButton = true,
                onClickBackButton = {
                    navController.popBackStack()
                },
                onClickAction1 = {}
            ) {}
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No hay Favoritos", color = Color.Black)
        }
    }
}