package com.oskr19.breakingbad.modules.characters.domain

import kotlinx.coroutines.flow.Flow

interface FetchCharactersRepository {
    suspend fun fetchRemoteCharacters(offset: Int): Flow<List<CharacterDTO>>
    suspend fun fetchLocalCharacters(): Flow<List<CharacterDTO>>
}