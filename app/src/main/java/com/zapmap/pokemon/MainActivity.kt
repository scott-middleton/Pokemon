package com.zapmap.pokemon

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.zapmap.pokemon.databinding.ActivityMainBinding
import com.zapmap.pokemon.features.pokemon_details.presentation.PokemonDetailActivity
import com.zapmap.pokemon.features.pokemon_details.presentation.PokemonDetailActivity.Companion.POKEMON_ID_EXTRA_ID
import com.zapmap.pokemon.features.pokemon_list.domain.mappers.model.UiPokemonItem
import com.zapmap.pokemon.features.pokemon_list.presentation.PokemonAdapter
import com.zapmap.pokemon.features.pokemon_list.presentation.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: PokemonListViewModel by viewModels()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val pokemonAdapter = PokemonAdapter { pokemonItem -> onPokemonClicked(pokemonItem) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerViewPokemon.adapter = pokemonAdapter
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.pokemonList.collectLatest { pagingData ->
                pokemonAdapter.submitData(pagingData)
            }
        }
    }

    private fun onPokemonClicked(pokemon: UiPokemonItem) {
        ZoogleAnalytics.logEvent(ZoogleAnalyticsEvent("pokemon_selected", mapOf("id" to pokemon.id.toString())))

        Intent(this@MainActivity, PokemonDetailActivity::class.java).apply {
            putExtra(POKEMON_ID_EXTRA_ID, pokemon.id)
            startActivity(this@apply)
        }
    }
}