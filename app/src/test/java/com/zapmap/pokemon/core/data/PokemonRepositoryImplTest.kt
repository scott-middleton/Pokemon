package com.zapmap.pokemon.core.data

import com.zapmap.pokemon.core.data.remote.PokemonApi
import com.zapmap.pokemon.core.data.repository.PokemonRepositoryImpl
import com.zapmap.pokemon.features.pokemon_list.data.paging.PokemonPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class PokemonRepositoryImplTest {

    private lateinit var api: PokemonApi
    private lateinit var repository: PokemonRepositoryImpl
    private lateinit var okHttpClient: OkHttpClient
    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(PokemonApi::class.java)
        repository = PokemonRepositoryImpl(
            api = api
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When fetchPokemonById() called with success, valid response is returned`() = runTest {
        // Arrange
        val fakePokemonId = 1
        val responseJson = """{
        "name": "bulbasaur",
        "weight": 69,
        "height": 7,
        "types": [
            {
                "type": {
                    "name": "grass",
                    "url": "https://example.com/type/1/"
                }
            }
        ],
        "sprites": {
            "front_default": "https://example.com/sprite/front_default.png"
        }
    }""".trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseJson).setResponseCode(200))

        // Act
        val result = repository.fetchPokemonById(fakePokemonId)

        // Assert
        result.onSuccess { uiPokemonDetail ->
            assertEquals("Bulbasaur", uiPokemonDetail.name)
            assertEquals(69, uiPokemonDetail.weight)
            assertEquals(7, uiPokemonDetail.height)
            assertEquals(listOf("grass"), uiPokemonDetail.typeNames)
            assertEquals("https://example.com/sprite/front_default.png", uiPokemonDetail.spriteFrontDefault)
        }
    }



    @Test
    fun `When fetchPokemonById() called with error, failure response is returned`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(400)
        )

        val result = repository.fetchPokemonById(1)

        assertTrue(result.isFailure)
    }

    @Test
    fun `When getPokemonPagingSource() called, PokemonPagingSource is returned`() {
        val pagingSource = repository.getPokemonPagingSource()
        assertTrue(pagingSource is PokemonPagingSource)
    }
}