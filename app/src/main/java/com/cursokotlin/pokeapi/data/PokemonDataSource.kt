package com.cursokotlin.pokeapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cursokotlin.pokeapi.model.PokemonList
import com.cursokotlin.pokeapi.repository.PokemonRepository
import okio.IOException

class PokemonDataSource(private val repo: PokemonRepository) : PagingSource<Int, PokemonList>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonList> {

        return try {
            // Pagina actual (offset), empieza en 0
            val offset = params.key ?: 0
            val response = repo.getPokemonsPaging(limit = params.loadSize, offset = offset)

            //Cargar siguiente y anterior página
            LoadResult.Page(
                data = response.results,
                prevKey = if(offset == 0) null else offset - params.loadSize,
                nextKey = if(response.next == null) null else offset + params.loadSize
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonList>): Int? {
        return state.anchorPosition
    }
}