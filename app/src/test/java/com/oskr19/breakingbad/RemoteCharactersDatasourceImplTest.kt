package com.oskr19.breakingbad

import com.oskr19.breakingbad.core.ErrorCodes
import com.oskr19.breakingbad.core.domain.Failure
import com.oskr19.breakingbad.modules.characters.data.CharactersAPI
import com.oskr19.breakingbad.modules.characters.data.RemoteCharactersDatasourceImpl
import com.oskr19.breakingbad.modules.characters.domain.CharacterDTO
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemoteCharactersDatasourceImplTest {

    private lateinit var sut: RemoteCharactersDatasourceImpl

    private val apiMock = mockk<CharactersAPI>()

    @Before
    fun setUp() {
        sut = RemoteCharactersDatasourceImpl(apiMock)
    }

    @Test
    fun `when fetch remote then should return success response`() = runBlocking {
        // Given
        val offset = 10
        val list = fillList()
        coEvery { apiMock.fetch(any()) } returns Response.success(list)

        //When
        sut.fetchCharacters(offset).collect {

            //Then
            assertTrue(it.isNotEmpty())
            assertEquals(it[0].charId, list[0].charId)
        }
    }

    @Test
    fun `when response is not success then should throw a failure`() = runBlocking {
        //Given
        val responseBody: ResponseBody = mockk {
            every { contentType() } returns "json".toMediaTypeOrNull()
            every { contentLength() } returns 1024
        }
        coEvery { apiMock.fetch(any()) } returns Response.error(ErrorCodes.NOT_FOUND, responseBody)

        //When
        sut.fetchCharacters(10).catch { cause ->
            assertNotNull(cause)
            assertTrue(cause is Failure.ServerError)
            cause.printStackTrace()
        }.collect()
    }

    private fun fillList(): List<CharacterDTO> {
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