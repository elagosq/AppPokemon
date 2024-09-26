package com.cursokotlin.pokeapi.data

import com.cursokotlin.pokeapi.model.AllPokemon
import com.cursokotlin.pokeapi.model.Pokemon
import com.cursokotlin.pokeapi.model.Species
import com.cursokotlin.pokeapi.util.Constants.Companion.ENDPOINT
import com.cursokotlin.pokeapi.util.Constants.Companion.ENPOINT_SPECIES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiPokemon {

    @GET(ENDPOINT)
    suspend fun getPokemon() : Response<AllPokemon>

    @GET(ENDPOINT)
    suspend fun getPokemonsPaging(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): AllPokemon

    @GET("$ENDPOINT/{name}")
    suspend fun getPokemonName(
        @Path("name") name: String
    ): Response<Pokemon>

    @GET("$ENPOINT_SPECIES/{name}")
    suspend fun getDescriptionSpecies(
        @Path("name") name: String
    ): Response<Species>

}