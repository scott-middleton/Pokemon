package com.zapmap.pokemon.features.pokemon_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.zapmap.pokemon.core.domain.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(repository: PokemonRepository) : ViewModel() {

    val pagingDataFlow = Pager(
        config = PagingConfig(pageSize = 50, enablePlaceholders = false),
        pagingSourceFactory = { repository.getPokemonPagingSource() }
    ).flow.cachedIn(viewModelScope)

}