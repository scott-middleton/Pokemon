package com.zapmap.pokemon

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.zapmap.pokemon.databinding.ActivityMainBinding
import com.zapmap.pokemon.features.pokemon_details.presentation.PokemonDetailActivity
import com.zapmap.pokemon.features.pokemon_details.presentation.PokemonDetailActivity.Companion.POKEMON_ID_EXTRA
import com.zapmap.pokemon.features.pokemon_list.domain.model.UiPokemonItem
import com.zapmap.pokemon.features.pokemon_list.presentation.PokemonAdapter
import com.zapmap.pokemon.features.pokemon_list.presentation.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        setupCollector()
    }

    private fun setupCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.pagingDataFlow.collect {
                pokemonAdapter.submitData(it)
            }
        }
    }

    private fun onPokemonClicked(pokemon: UiPokemonItem) {
        ZoogleAnalytics.logEvent(
            ZoogleAnalyticsEvent(
                "pokemon_selected",
                mapOf("id" to pokemon.id.toString())
            )
        )

        Intent(this@MainActivity, PokemonDetailActivity::class.java).apply {
            putExtra(POKEMON_ID_EXTRA, pokemon.id)
            startActivity(this@apply)
        }
    }
}