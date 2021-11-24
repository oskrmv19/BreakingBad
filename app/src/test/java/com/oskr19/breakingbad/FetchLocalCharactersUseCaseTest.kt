package com.oskr19.breakingbad

import com.oskr19.breakingbad.core.domain.Failure
import com.oskr19.breakingbad.modules.characters.domain.FetchCharactersRepository
import com.oskr19.breakingbad.modules.characters.domain.FetchLocalCharactersUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class FetchLocalCharactersUseCaseTest {

    private lateinit var sut: FetchLocalCharactersUseCase

    private val repositoryMock = mockk<FetchCharactersRepository>()

    @Before
    fun setUp() {
        sut = FetchLocalCharactersUseCase(repositoryMock)
    }

    @Test
    fun `when the use case runs then should return a list`() = runBlocking {
        //Given
        coEvery { repositoryMock.fetchLocalCharacters() } returns flowOf(listOf())

        //When
        sut.run(FetchLocalCharactersUseCase.Params("")).collect {

            //Then
            assertNotNull("The list should be not null", it)
            coVerify { repositoryMock.fetchLocalCharacters() }
        }
    }

    @Test
    fun `when the use case runs then should throw a failure`() = runBlocking {
        //Given
        coEvery { repositoryMock.fetchLocalCharacters() } returns flow { throw Failure.NoConnection }

        //When
        sut.run(FetchLocalCharactersUseCase.Params("")).catch { cause ->

            //Then
            assertNotNull(cause)
            assertEquals(cause, Failure.NoConnection)

        }.collect()

    }
}