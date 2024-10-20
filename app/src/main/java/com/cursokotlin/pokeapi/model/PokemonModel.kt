package com.cursokotlin.pokeapi.model

import com.google.gson.annotations.SerializedName

data class AllPokemon(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonList>
)

data class PokemonList(
    val name: String,
    val url: String
)

data class Pokemon(
    val name: String,
    val id: Int,
    val sprites: Sprites,
    val types: List<Type>
)

data class Sprites(
    val other: Other
)
data class Other(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String
)

data class Type(
    val slot: Int,
    val type: TypeX
)

data class TypeX(
    val name: String
)

data class Species(
    val flavor_text_entries: List<Flavor>
)

data class Flavor(
    val flavor_text: String,
)


