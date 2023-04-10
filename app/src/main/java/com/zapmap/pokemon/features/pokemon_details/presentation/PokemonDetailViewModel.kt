package com.zapmap.pokemon.features.pokemon_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapmap.pokemon.R
import com.zapmap.pokemon.core.domain.PokemonRepository
import com.zapmap.pokemon.core.util.UiText
import com.zapmap.pokemon.features.pokemon_details.domain.model.UiPokemonDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(
        PokemonDetailState(
            pokemonDetail = null,
            isLoading = true
        )
    )
    val uiState: StateFlow<PokemonDetailState> get() = _uiState

    private val errorChannel = Channel<UiText>(Channel.BUFFERED)
    val errorFlow: Flow<UiText> get() = errorChannel.receiveAsFlow()

    fun fetchPokemonDetails(id: Int) {
        viewModelScope.launch {
            val result = repository.fetchPokemonById(id)

            result.onSuccess { pokemonDetail ->
                _uiState.update {
                    it.copy(pokemonDetail = pokemonDetail, isLoading = false)
                }
            }.onFailure {
                _uiState.update {
                    it.copy(isLoading = false)
                }
                errorChannel.send(UiText.StringResource(R.string.generic_error_details))
            }
        }
    }

    data class PokemonDetailState(val pokemonDetail: UiPokemonDetail?, val isLoading: Boolean)
}