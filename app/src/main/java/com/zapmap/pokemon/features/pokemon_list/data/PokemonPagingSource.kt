package com.zapmap.pokemon.features.pokemon_list.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zapmap.pokemon.core.data.PokemonApi
import com.zapmap.pokemon.features.pokemon_list.domain.toUiPokemon
import com.zapmap.pokemon.features.pokemon_list.domain.mappers.model.UiPokemonItem
import retrofit2.HttpException
import java.io.IOException

class PokemonPagingSource(private val pokemonApi: PokemonApi) : PagingSource<Int, UiPokemonItem>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiPokemonItem> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = pokemonApi.fetchPokemons(limit = params.loadSize, offset = position * params.loadSize)

            if (response.isSuccessful) {
                val results = response.body()?.pokemonItems ?: emptyList()
                LoadResult.Page(
                    data = results.map { it.toUiPokemon() },
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - params.loadSize,
                    nextKey = if (results.isEmpty()) null else position + params.loadSize
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UiPokemonItem>): Int? {
        return state.anchorPosition
    }
}
