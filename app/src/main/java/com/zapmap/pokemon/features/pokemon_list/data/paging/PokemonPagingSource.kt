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
        private const val STARTING_POSITION = 0
        private const val PAGE_SIZE = 100
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiPokemonItem> {
        val position = params.key ?: STARTING_POSITION
        return try {
            val response = pokemonApi.fetchPokemons(limit = PAGE_SIZE, offset = position)

            if (response.isSuccessful) {
                val results = response.body()?.pokemonItems ?: emptyList()
                LoadResult.Page(
                    data = results.map { it.toUiPokemon() },
                    prevKey = if (position == STARTING_POSITION) null else position - PAGE_SIZE,
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
