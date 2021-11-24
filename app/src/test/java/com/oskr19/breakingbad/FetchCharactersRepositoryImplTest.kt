package com.oskr19.breakingbad

import com.oskr19.breakingbad.modules.characters.data.CharacterMapper
import com.oskr19.breakingbad.modules.characters.data.FetchCharactersRepositoryImpl
import com.oskr19.breakingbad.modules.characters.data.LocalCharactersDatasource
import com.oskr19.breakingbad.modules.characters.data.RemoteCharactersDatasource
import com.oskr19.breakingbad.modules.characters.domain.CharacterDTO
import com.oskr19.breakingbad.modules.favorites.data.LocalFavoriteDatasource
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchCharactersRepositoryImplTest {

    private lateinit var sut: FetchCharactersRepositoryImpl

    private val mapper = CharacterMapper()
    private val localCharactersDatasourceMock = mockk<LocalCharactersDatasource>()
    private val remoteRemoteCharactersDatasourceMock = mockk<RemoteCharactersDatasource>()
    private val localFavoriteDatasourceMock = mockk<LocalFavoriteDatasource>()

    @Before
    fun setUp() {
        sut = FetchCharactersRepositoryImpl(
            mapper,
            localCharactersDatasourceMock,
            remoteRemoteCharactersDatasourceMock,
            localFavoriteDatasourceMock
        )
    }

    @Test
    fun `when fetch remote then should save local`() = runBlocking {
        // Given
        val offset = 10
        val list = fillFakeList()
        coEvery { remoteRemoteCharactersDatasourceMock.fetchCharacters(offset) } returns flowOf(list)
        coEvery { localCharactersDatasourceMock.saveCharacter(any()) } just Runs
        coEvery { localFavoriteDatasourceMock.getById(1) } returns mockk()

        //When
        sut.fetchRemoteCharacters(offset).collect()

        //Then
        coVerify { localCharactersDatasourceMock.saveCharacter(any()) }
        coVerify { localFavoriteDatasourceMock.getById(1) }
    }

    private fun fillFakeList(): List<CharacterDTO> {
        return listOf(
            CharacterDTO(
                charId = 1,
                name = "name",
                birthday = "birthday",
                occupation = arrayListOf("occupation"),
                img = "img",
                status = "status",
                nickname = "nickname",
                portrayed = "portrayed"
            )
        )
    }
}