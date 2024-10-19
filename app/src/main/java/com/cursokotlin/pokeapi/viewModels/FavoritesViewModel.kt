package com.cursokotlin.pokeapi.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.pokeapi.model.PokemonList
import com.cursokotlin.pokeapi.store.FavoritesDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class FavoritesViewModel(application: Application) : AndroidViewModel(application){
    private val favoriteDataStore = FavoritesDataStore(application)

    private val _favoriteItemList = MutableStateFlow<List<PokemonList>>(emptyList())
    val favoriteItemList: StateFlow<List<PokemonList>> = _favoriteItemList

    init {
        loadFavoriteItemList()
    }

    //Cargar la lista de Item favoritos desde DataStore
    private fun loadFavoriteItemList() {
        viewModelScope.launch {
            favoriteDataStore.favoriteItemListFlow.collect { list ->
                _favoriteItemList.value = list
            }
        }
    }


    //Agregar un Item a favoritos
    fun addFavoriteItem(favorite : PokemonList){
        viewModelScope.launch {
            favoriteDataStore.addFavoriteItem(favorite)
        }
    }

    //Elimina un Item a favoritos
    fun removeFavoriteItem(favorite: PokemonList){
        viewModelScope.launch {
            favoriteDataStore.removeFavoriteItem(favorite)
        }
    }
}