package com.cursokotlin.pokeapi.views.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cursokotlin.pokeapi.components.ItemRow
import com.cursokotlin.pokeapi.components.MainTopBar
import com.cursokotlin.pokeapi.model.PokemonList
import com.cursokotlin.pokeapi.viewModels.FavoritesViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteView(navController: NavController, viewModel: FavoritesViewModel = viewModel()) {
    val favoriteItemList by viewModel.favoriteItemList.collectAsState()
    println(favoriteItemList)

    Scaffold(
        topBar = {
            MainTopBar(
                title = "Pokémon Favoritos",
                showBackButton = true,
                onClickBackButton = {
                    navController.popBackStack()
                },
                onClickAction1 = {}
            ) {}
        }
    ) {
        ContentFavoriteView(it, viewModel, favoriteItemList)
    }
}

@Composable
fun ContentFavoriteView(
    pad: PaddingValues,
    viewModel: FavoritesViewModel,
    favoriteItemList: List<PokemonList>
) {
    Column(
        modifier = Modifier
            .padding(pad)
            .background(Color.White)
            .fillMaxSize()
    ) {
        if (favoriteItemList.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(favoriteItemList.size) { index ->
                    val item = favoriteItemList[index]
                    ItemRow(item, viewModel, favoriteItemList) {}
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No hay Item en favoritos", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
