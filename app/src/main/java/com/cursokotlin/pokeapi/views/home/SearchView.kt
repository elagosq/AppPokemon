package com.cursokotlin.pokeapi.views.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cursokotlin.pokeapi.components.MainTopBar
import com.cursokotlin.pokeapi.util.Constants.Companion.CUSTOM_GREEN
import com.cursokotlin.pokeapi.viewModels.PokemonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchView(navController: NavController, pokemonVM: PokemonViewModel = hiltViewModel()) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val games by pokemonVM.pokemons.collectAsState()

    Scaffold(
        topBar = {
            MainTopBar(
                title = "Buscar",
                showBackButton = true,
                onClickBackButton = {
                    navController.popBackStack()
                },
                onClickAction1 = {}
            ) {}
        }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 70.dp, horizontal = 20.dp),
                query = query,
                onQueryChange = { query = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = "Search") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = Color(CUSTOM_GREEN)
                    )
                },
                trailingIcon = {
                    if(query != ""){
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier.clickable { query = "" }, tint = Color(CUSTOM_GREEN)
                        )
                    }
                }
            ) {
                if (query.isNotEmpty()) {
                    val filterGames = games.filter { it.name.contains(query, ignoreCase = true) }
                    filterGames.forEach {
                        Text(
                            text = it.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 10.dp, start = 10.dp)
                                .clickable {
                                    navController.navigate("DetailView/${it.name}")
                                }
                        )
                    }
                }
            }
        }
    }
}