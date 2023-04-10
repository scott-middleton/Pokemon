package com.zapmap.pokemon.core.data.repository

import androidx.paging.PagingSource
import com.zapmap.pokemon.core.data.remote.PokemonApi
import com.zapmap.pokemon.core.domain.PokemonRepository
import com.zapmap.pokemon.features.pokemon_details.domain.mapper.toUiPokemonDetail
import com.zapmap.pokemon.features.pokemon_details.domain.model.UiPokemonDetail
import com.zapmap.pokemon.features.pokemon_list.domain.model.UiPokemonItem
import com.zapmap.pokemon.features.pokemon_list.data.paging.PokemonPagingSource

class PokemonRepositoryImpl(private val api: PokemonApi) : PokemonRepository {

    override suspend fun fetchPokemonById(id: Int): Result<UiPokemonDetail> {
        return try {
            val response = api.fetchPokemonById(id)
            return if (response.isSuccessful) {
                Result.success(response.body()!!.toUiPokemonDetail())
            } else {
                Result.failure(Exception(Throwable()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getPokemonPagingSource(): PagingSource<Int, UiPokemonItem> {
        return PokemonPagingSource(api)
    }
}