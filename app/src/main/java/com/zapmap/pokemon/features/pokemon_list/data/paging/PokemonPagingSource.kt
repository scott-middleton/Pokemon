package com.zapmap.pokemon.features.pokemon_list.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zapmap.pokemon.core.data.remote.PokemonApi
import com.zapmap.pokemon.features.pokemon_list.domain.mappers.toUiPokemon
import com.zapmap.pokemon.features.pokemon_list.domain.model.UiPokemonItem
import retrofit2.HttpException
import java.io.IOException

class PokemonPagingSource(private val pokemonApi: PokemonApi) : PagingSource<Int, UiPokemonItem>() {

    companion object {
        private const val STARTING_POSITION = 0 // using a slightly more descriptive name
        private const val PAGE_SIZE = 100 // new constant to define the size of pages
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiPokemonItem> {
        val position = params.key ?: STARTING_POSITION
        return try {
            val response = pokemonApi.fetchPokemons(
                limit = PAGE_SIZE, // always using the same constant for the page size
                offset = position  // fixed a bug that caused the call for the second page to have an offset of 7500
            )

            if (response.isSuccessful) {
                val results = response.body()?.pokemonItems ?: emptyList()
                LoadResult.Page(
                    data = results.map { it.toUiPokemon() },
                    prevKey = if (position == STARTING_POSITION) null else position - PAGE_SIZE, // we control the page size directly here
                    nextKey = if (results.isEmpty()) null else position + PAGE_SIZE
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
