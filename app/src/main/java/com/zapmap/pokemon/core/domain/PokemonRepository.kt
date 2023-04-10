package com.zapmap.pokemon.core.domain

import androidx.paging.PagingSource
import com.zapmap.pokemon.features.pokemon_details.domain.model.UiPokemonDetail
import com.zapmap.pokemon.features.pokemon_list.domain.model.UiPokemonItem

interface PokemonRepository {
    suspend fun fetchPokemonById(id: Int): Result<UiPokemonDetail>
    fun getPokemonPagingSource(): PagingSource<Int, UiPokemonItem>
}