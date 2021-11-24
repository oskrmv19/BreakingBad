package com.oskr19.breakingbad.modules.characters.data

import com.oskr19.breakingbad.core.EndPoints
import com.oskr19.breakingbad.modules.characters.domain.CharacterDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CharactersAPI {
    @JvmSuppressWildcards
    @GET(EndPoints.CHARACTERS)
    suspend fun fetch(
        @QueryMap params: Map<String, Any>
    ): Response<List<CharacterDTO>>
}
