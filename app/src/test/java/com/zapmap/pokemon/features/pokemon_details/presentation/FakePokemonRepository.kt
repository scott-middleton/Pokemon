package com.zapmap.pokemon.features.pokemon_details.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zapmap.pokemon.core.domain.PokemonRepository
import com.zapmap.pokemon.features.pokemon_details.domain.model.UiPokemonDetail
import com.zapmap.pokemon.features.pokemon_list.domain.model.UiPokemonItem
import kotlinx.coroutines.delay

class FakePokemonRepository : PokemonRepository {
    var shouldReturnError = false

    private val pokemonDetails = mapOf(
        1 to UiPokemonDetail(
            name = "bulbasaur",
            spriteFrontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            weight = 100,
            height = 100,
            typeNames = listOf("example")
        )
    )

    override suspend fun fetchPokemonById(id: Int): Result<UiPokemonDetail> {
        if (shouldReturnError) {
            return Result.failure(Exception("Something went wrong"))
        }

        delay(500) // Simulate network delay
        return Result.success(providePokemonDetail(id))
    }

    override fun getPokemonPagingSource(): PagingSource<Int, UiPokemonItem> {
        return object : PagingSource<Int, UiPokemonItem>() {
            override fun getRefreshKey(state: PagingState<Int, UiPokemonItem>): Int? {
                return null
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiPokemonItem> {
                return LoadResult.Page(emptyList(), null, null)
            }

        }
    }

    fun providePokemonDetail(id: Int): UiPokemonDetail {
        return pokemonDetails[id]!!
    }
}