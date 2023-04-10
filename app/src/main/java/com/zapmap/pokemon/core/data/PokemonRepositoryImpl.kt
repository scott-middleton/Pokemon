package com.zapmap.pokemon.core.data

import androidx.paging.PagingSource
import com.zapmap.pokemon.RemotePokemon
import com.zapmap.pokemon.core.domain.PokemonRepository
import com.zapmap.pokemon.core.domain.model.UiPokemon
import com.zapmap.pokemon.features.pokemon_list.data.PokemonPagingSource

class PokemonRepositoryImpl(private val api: PokemonApi) : PokemonRepository {

    override suspend fun fetchPokemonById(id: Int): RemotePokemon? {
        val response = api.fetchPokemonById(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Handle error case
            null
        }
    }

    override fun getPokemonPagingSource(): PagingSource<Int, UiPokemon> {
        return PokemonPagingSource(api)
    }
}