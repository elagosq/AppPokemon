package com.cursokotlin.pokeapi.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.cursokotlin.pokeapi.data.PokemonDataSource
import com.cursokotlin.pokeapi.model.PokemonList
import com.cursokotlin.pokeapi.repository.PokemonRepository
import com.cursokotlin.pokeapi.state.DescriptSpeciesState
import com.cursokotlin.pokeapi.state.PokemonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class PokemonViewModel @Inject constructor(private val repo: PokemonRepository) : ViewModel() {

    //Flow obtener las lista de pokemones
    private val _pokemons = MutableStateFlow<List<PokemonList>>(emptyList())
    val pokemons = _pokemons.asStateFlow()

    var state by mutableStateOf(PokemonState())
        private set

    var specieState by mutableStateOf(DescriptSpeciesState())

    init {
        fetchPokemons()
    }

    val pokemonsPage =
        Pager(PagingConfig(pageSize = 6)) {
            PokemonDataSource(repo)
        }.flow.cachedIn(viewModelScope) //cachedIn una funcionalidad que permite manejar los datos que se esta obteniendo de la paginación y se guardan en cache

    private fun fetchPokemons() {
        //Consulta a la api rest
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = repo.getPokemon()
                _pokemons.value = result ?: emptyList() //En el caso que retorne vacio, se agrega un valor opcional vacio
            }
        }
    }

    fun getPokemonByName(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = repo.getPokemonName(name)
                state = state.copy(
                    id = result?.id ?: 0,
                    front_default = result?.sprites?.other?.officialArtwork?.frontDefault ?: "",
                    types = result?.types!!,
                    name = result?.name ?: "",
                )
            }
        }
    }

    fun getDescriptionSpecies(name: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = repo.getDescripSpecies(name)
                specieState = specieState.copy(
                    text_description = result!!.flavor_text_entries[0].flavor_text
                )
            }
        }
    }
}