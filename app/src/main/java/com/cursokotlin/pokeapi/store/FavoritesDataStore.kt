package com.cursokotlin.pokeapi.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cursokotlin.pokeapi.model.PokemonList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Inicialización de DataStore
val Context.dataStore by preferencesDataStore(name = "item_favorites")

class FavoritesDataStore(private val context: Context) {

    private val favoritelistitemkey = stringPreferencesKey("favorite_item_list")
    private val gson = Gson()

    //Obtener el flujo de favoritos
    val favoriteItemListFlow: Flow<List<PokemonList>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[favoritelistitemkey] ?: ""
            if(jsonString.isNotEmpty()) {
                // Deserializar la cadena JSON a una lista de objetos Item
                val listType = object : TypeToken<List<PokemonList>>() {}.type
                gson.fromJson(jsonString, listType)
            } else {
                emptyList()
            }
        }


    // Agregar un Item a favoritos
    suspend fun addFavoriteItem(favorite: PokemonList) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[favoritelistitemkey]?.let { jsonString ->
                val listType = object : TypeToken<List<PokemonList>>() {}.type
                gson.fromJson<List<PokemonList>>(jsonString, listType)
            } ?: emptyList()

            val updatedFavorites = currentFavorites + favorite
            preferences[favoritelistitemkey] = gson.toJson(updatedFavorites)
        }
    }

    // Eliminar un Item de favoritos
    suspend fun removeFavoriteItem(favorite: PokemonList) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[favoritelistitemkey]?.let { jsonString ->
                val listType = object : TypeToken<List<PokemonList>>() {}.type
                gson.fromJson<List<PokemonList>>(jsonString, listType)
            } ?: emptyList()

            val updatedFavorites = currentFavorites.filter { it.name != favorite.name }
            preferences[favoritelistitemkey] = gson.toJson(updatedFavorites)
        }
    }
}