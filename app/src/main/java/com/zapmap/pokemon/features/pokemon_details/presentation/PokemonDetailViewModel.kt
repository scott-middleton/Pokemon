package com.zapmap.pokemon.features.pokemon_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapmap.pokemon.RemotePokemon
import com.zapmap.pokemon.core.domain.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {

    private val _pokemonDetails = MutableLiveData<RemotePokemon>()
    val pokemonDetails: LiveData<RemotePokemon> get() = _pokemonDetails

    fun fetchPokemonDetails(id: Int) {
        viewModelScope.launch {
            val result = repository.fetchPokemonById(id)
            result?.let {
                _pokemonDetails.value = it
            }
        }
    }
}