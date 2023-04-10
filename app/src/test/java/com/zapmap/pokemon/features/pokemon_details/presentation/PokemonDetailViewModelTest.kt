package com.zapmap.pokemon.features.pokemon_details.presentation

import app.cash.turbine.test
import com.zapmap.pokemon.R
import com.zapmap.pokemon.core.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailViewModelTest {

    private lateinit var fakePokemonRepository: FakePokemonRepository
    private lateinit var viewModel: PokemonDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        fakePokemonRepository = FakePokemonRepository()
        viewModel = PokemonDetailViewModel(fakePokemonRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When fetchPokemonDetails() called with success, valid response is returned`() = runTest {
        val id = 1
        viewModel.fetchPokemonDetails(id)

        val state = viewModel.uiState.drop(1).first().pokemonDetail
        val expected = fakePokemonRepository.providePokemonDetail(id)
        assertEquals(expected, state)
    }

    @Test
    fun `When fetchPokemonDetails() called with failure, error is returned`() = runTest {
        fakePokemonRepository.shouldReturnError = true
        val id = 1
        viewModel.fetchPokemonDetails(id)

        viewModel.errorFlow.test {
            val error = awaitItem()
            assertEquals(UiText.StringResource(R.string.generic_error_details), error)
        }
    }
}