package com.zapmap.pokemon.features.pokemon_details.presentation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.zapmap.pokemon.databinding.ActivityPokemonDetailBinding
import com.zapmap.pokemon.features.pokemon_details.domain.model.UiPokemonDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailActivity : AppCompatActivity() {
    companion object {
        const val POKEMON_ID_EXTRA = "POKEMON_ID_EXTRA_ID"
    }

    private lateinit var binding: ActivityPokemonDetailBinding
    private val viewModel: PokemonDetailViewModel by viewModels()

    private val typesAdapter = TypesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.recyclerViewTypes.adapter = typesAdapter

        intent?.getIntExtra(POKEMON_ID_EXTRA, -1)?.let { pokemonId ->
            if (pokemonId != -1) {
                viewModel.fetchPokemonDetails(pokemonId)
            }
        }

        setupCollectors()
    }

    private fun setupCollectors() {
        lifecycleScope.launchWhenStarted {
            viewModel.errorFlow.collect { errorMessage ->
                val context = this@PokemonDetailActivity
                Toast.makeText(
                    context,
                    errorMessage.asString(context),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiPokemonDetail ->
                binding.loadingIndicator.apply {
                    visibility = if (uiPokemonDetail.isLoading) {
                        VISIBLE
                    } else {
                        GONE
                    }
                }

                uiPokemonDetail.pokemonDetail?.let {
                    displayPokemon(it)
                }
            }
        }
    }

    private fun displayPokemon(pokemon: UiPokemonDetail) {
        binding.apply {
            textViewPokemonName.text = pokemon.name
            textViewWeight.text = pokemon.weight.toString()
            textViewHeight.text = pokemon.height.toString()
            imageViewPokemonFront.load(pokemon.spriteFrontDefault)
            typesAdapter.updateItems(pokemon.typeNames)
        }
    }
}