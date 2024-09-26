package com.cursokotlin.pokeapi.views.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cursokotlin.pokeapi.components.MainImage
import com.cursokotlin.pokeapi.components.MainTopBar
import com.cursokotlin.pokeapi.components.SpacerH
import com.cursokotlin.pokeapi.util.Constants.Companion.CUSTOM_GREEN
import com.cursokotlin.pokeapi.viewModels.PokemonViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailView(
    navController: NavController,
    name: String,
    pokemonVM: PokemonViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        pokemonVM.getPokemonByName(name)
        pokemonVM.getDescriptionSpecies(name)
    }

    Scaffold(
        topBar = {
            MainTopBar(
                title = name,
                showBackButton = true,
                onClickBackButton = {
                    navController.popBackStack()
                },
                onClickAction1 = {}
            ) {}
        }
    ) {
        ContentDetailView(it, pokemonVM)
    }
}

@Composable
fun ContentDetailView(
    it: PaddingValues,
    pokemonVM: PokemonViewModel
) {
    val state = pokemonVM.state
    val stateSpecie = pokemonVM.specieState
    Column(
        modifier = Modifier
            .padding(it)
            .fillMaxSize()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(vertical = 20.dp)
                .background(Color(CUSTOM_GREEN)),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MainImage(state.front_default, w = 300, h = 300)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = state.name,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp
                    )
                    Text(
                        text = "#00${state.id.toString()}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }

            }
        }
        SpacerH()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            state.types.forEach { type ->
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp)
                        .background(Color(CUSTOM_GREEN)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = type.type.name,
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                    )
                }
            }
        }
        SpacerH()
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                textAlign = TextAlign.Justify,
                text = stateSpecie.text_description,
                fontSize = 22.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
