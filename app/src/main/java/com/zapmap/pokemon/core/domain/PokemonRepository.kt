package com.zapmap.pokemon.core.domain

import androidx.paging.PagingSource
import com.zapmap.pokemon.RemotePokemon
import com.zapmap.pokemon.core.domain.model.UiPokemon

interface PokemonRepository {
    suspend fun fetchPokemonById(id: Int): RemotePokemon?
    fun getPokemonPagingSource(): PagingSource<Int, UiPokemon>
}