package com.cursokotlin.pokeapi.state

import com.cursokotlin.pokeapi.model.Type


data class PokemonState(
    val id: Int = 0,
    val front_default: String = "",
    val types: List<Type> = emptyList(),
    val name: String = "",
)
