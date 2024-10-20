package com.cursokotlin.pokeapi.repository

import androidx.compose.ui.geometry.Offset
import com.cursokotlin.pokeapi.data.ApiPokemon
import com.cursokotlin.pokeapi.model.AllPokemon
import com.cursokotlin.pokeapi.model.Pokemon
import com.cursokotlin.pokeapi.model.PokemonList
import com.cursokotlin.pokeapi.model.Species
import kotlinx.coroutines.delay
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val apiPokemon: ApiPokemon) {

    suspend fun getPokemon(): List<PokemonList> ? {
        val response = apiPokemon.getPokemon()
        if(response.isSuccessful){
            return response.body()?.results
        }
        return null
    }

    suspend fun getPokemonsPaging(offset : Int,limit: Int): AllPokemon{
        delay(3000L)
        return apiPokemon.getPokemonsPaging(offset,limit)
    }

    suspend fun getPokemonName(name: String) : Pokemon? {
        val response = apiPokemon.getPokemonName(name)

        if(response.isSuccessful){
            return response.body()
        }

        return null
    }

    suspend fun getDescripSpecies(name: String) : Species?{
        val response = apiPokemon.getDescriptionSpecies(name)

        if(response.isSuccessful){
            return response.body()
        }

        return null
    }
}