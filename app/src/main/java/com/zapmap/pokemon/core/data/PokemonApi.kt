package com.zapmap.pokemon.core.data

import com.zapmap.pokemon.RemotePokemon
import com.zapmap.pokemon.features.pokemon_list.data.dto.PokemonsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface PokemonApi {
    @GET("pokemon")
    suspend fun fetchPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonsResponse>

    @GET("pokemon/{id}")
    suspend fun fetchPokemonById(
        @Path("id") id: Int,
    ): Response<RemotePokemon>
}