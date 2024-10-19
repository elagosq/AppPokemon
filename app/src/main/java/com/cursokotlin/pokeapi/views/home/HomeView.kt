package com.cursokotlin.pokeapi.views.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.cursokotlin.pokeapi.components.ButtonGoBack
import com.cursokotlin.pokeapi.components.ItemRow
import com.cursokotlin.pokeapi.components.Loading
import com.cursokotlin.pokeapi.components.MainTopBar
import com.cursokotlin.pokeapi.util.Constants.Companion.CUSTOM_GREEN
import com.cursokotlin.pokeapi.viewModels.FavoritesViewModel
import com.cursokotlin.pokeapi.viewModels.PokemonViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(
    navController: NavController,
    pokemonVM: PokemonViewModel = hiltViewModel(),
    viewModel: FavoritesViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            MainTopBar(
                title = "POKE API",
                onClickBackButton = {},
                onClickAction1 = { navController.navigate("UserView") }
            ) {
                navController.navigate("SearchView")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color(CUSTOM_GREEN),
                contentColor = Color.White,
                onClick = { navController.navigate("FavoriteView") },
                shape = CircleShape,
            ) {
                Icon(Icons.Default.Favorite, "Ir a la pantalla favorito")
            }
        }
    ) {
        ContentHomeView(it, navController, pokemonVM, viewModel)
    }
}

@Composable
fun ContentHomeView(
    pad: PaddingValues,
    navController: NavController,
    pokemonVM: PokemonViewModel,
    viewModel: FavoritesViewModel
) {
    val pokemonsPage =
        pokemonVM.pokemonsPage.collectAsLazyPagingItems() //Obtener los datos de la paginación
    val favoriteItemList by viewModel.favoriteItemList.collectAsState()

    ButtonGoBack()
    Column(
        modifier = Modifier
            .padding(pad)
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (pokemonsPage.itemCount == 0) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Loading()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(bottom = 10.dp)
            ) {
                items(pokemonsPage.itemCount) { index ->
                    val item = pokemonsPage[index]
                    if (item != null) {
                        ItemRow(item, viewModel, favoriteItemList) {
                            navController.navigate("DetailView/${item.name}")
                        }
                    }
                }
                when (pokemonsPage.loadState.append) { //append cuando se haya agregado datos
                    is LoadState.NotLoading -> Unit
                    LoadState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier.fillParentMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Loading()
                            }
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            Text("Error al cargar")
                        }
                    }
                }

            }
        }
    }
}



