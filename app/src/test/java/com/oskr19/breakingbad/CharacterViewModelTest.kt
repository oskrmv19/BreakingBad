package com.oskr19.breakingbad

import androidx.test.core.app.ApplicationProvider
import com.oskr19.breakingbad.core.domain.Failure
import com.oskr19.breakingbad.core.presentation.Event
import com.oskr19.breakingbad.modules.characters.domain.CharacterDTO
import com.oskr19.breakingbad.modules.characters.domain.FetchLocalCharactersUseCase
import com.oskr19.breakingbad.modules.characters.domain.FetchRemoteCharactersUseCase
import com.oskr19.breakingbad.modules.characters.presentation.CharactersViewModel
import com.oskr19.breakingbad.modules.favorites.data.SetFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import java.sql.Timestamp
import java.util.Calendar
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CharacterViewModelTest {

    private lateinit var sut: CharactersViewModel

    private val fetchRemoteCharactersUseCaseMock = mockk<FetchRemoteCharactersUseCase>()
    private val fetchLocalCharactersUseCaseMock = mockk<FetchLocalCharactersUseCase>()
    private val setFavoriteUseCaseMock = mockk<SetFavoriteUseCase>()

    @Before
    fun setUp() {
        sut = CharactersViewModel(
            ApplicationProvider.getApplicationContext(),
            fetchRemoteCharactersUseCaseMock,
            fetchLocalCharactersUseCaseMock,
            setFavoriteUseCaseMock
        )
    }

    @Test
    fun `when fetch remote should return success`() = runBlocking {
        //Given
        val offset = 10
        val params = FetchRemoteCharactersUseCase.Params(offset)
        val fakeReturn = flowOf(listOf<CharacterDTO>())
        coEvery { fetchRemoteCharactersUseCaseMock.run(params) } returns fakeReturn

        //When
        sut.fetchFromRemote(offset)

        //Then
        sut.characters.observeForever {
            assertNotNull("The value should be not null", it)
        }

        sut.status.observeForever {
            assertEquals("the value should be equals", Event.FINISHED, it)
        }
    }

    @Test
    fun `when fetch remote throw an failure then should catch and handle it`() = runBlocking {
        //Given
        val offset = 10
        val params = FetchRemoteCharactersUseCase.Params(offset)
        val exceptions = listOf(
            Event.DISCONNECTED to Failure.NoConnection,
            Event.ERROR(Failure.ServerError()) to Failure.ServerError()
        )
        val exception = exceptions.random()
        coEvery { fetchRemoteCharactersUseCaseMock.run(params) } throws exception.second

        //When
        sut.fetchFromRemote(offset)

        //Then
        sut.characters.observeForever {
            assertNull("The value should be null", it)
        }

        sut.status.observeForever {
            assertEquals("the value should be equals", exception.first, it)
        }
    }

    @Test
    fun `when set favorite then should return success`() = runBlocking {
        //Given
        val characterId = 10
        val isFavorite = true
        val params = SetFavoriteUseCase.Params(characterId, isFavorite)
        val fakeReturn = flowOf(characterId)
        coEvery { setFavoriteUseCaseMock.run(params) } returns fakeReturn

        //When
        sut.setFavorite(characterId, isFavorite)

        //Then
        coVerify { setFavoriteUseCaseMock.run(params) }
    }

    @Test
    fun `when fetch local then the final list should be ordered`() = runBlocking {
        //Given
        val offset = 10
        val params = FetchLocalCharactersUseCase.Params(offset)

        //Order given 1,2,3,4
        val unsortedList = generateList()
        val fakeReturn = flowOf(unsortedList)
        coEvery { fetchLocalCharactersUseCaseMock.run(any()) } returns fakeReturn

        //Then
        sut.characters.observeForever { sortedList ->
            assertNotNull("The value should be not null", sortedList)

            //Order Expected 4,2,1,3
            assertEquals(sortedList[0], unsortedList[3])
            assertEquals(sortedList[1], unsortedList[1])
            assertEquals(sortedList[2], unsortedList[0])
            assertEquals(sortedList[3], unsortedList[2])
        }

        //When
        sut.fetchFromLocal()
    }

    private fun generateList(): List<CharacterDTO> {
        var calendar = Calendar.getInstance()
        return listOf(
            mockk {
                every { isFavorite } returns false
                calendar.set(2021, 11, 24, 12, 0, 11)
                every { createdAt } returns Timestamp(calendar.time.time)
            },
            mockk {
                every { isFavorite } returns false
                calendar = Calendar.getInstance()
                calendar.set(2021, 11, 24, 12, 0, 22)
                every { createdAt } returns Timestamp(calendar.time.time)
            },
            mockk {
                every { isFavorite } returns false
                calendar = Calendar.getInstance()
                calendar.set(2021, 11, 24, 12, 0, 33)
                every { createdAt } returns Timestamp(calendar.time.time)
            },
            mockk {
                every { isFavorite } returns false
                calendar = Calendar.getInstance()
                calendar.set(2021, 11, 24, 12, 0, 14)
                every { createdAt } returns Timestamp(calendar.time.time)
            },
        )
    }
}