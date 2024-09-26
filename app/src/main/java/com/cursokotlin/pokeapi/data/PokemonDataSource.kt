package com.cursokotlin.pokeapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cursokotlin.pokeapi.model.PokemonList
import com.cursokotlin.pokeapi.repository.PokemonRepository
import okio.IOException

class PokemonDataSource(private val repo: PokemonRepository): PagingSource<Int, PokemonList>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonList>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonList> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = repo.getPokemonsPaging(nextPageNumber, 6)
            LoadResult.Page(
               data = response.results,
               prevKey = null,
               nextKey = if(response.results.isNotEmpty())  nextPageNumber + 1  else null
            )
        }catch (exception: IOException){
            LoadResult.Error(exception)
        }
    }
}