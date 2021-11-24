package com.oskr19.breakingbad.modules.characters.data

import com.oskr19.breakingbad.modules.characters.domain.CharacterDTO
import kotlinx.coroutines.flow.Flow

interface RemoteCharactersDatasource {
    suspend fun fetchCharacters(offset: Int): Flow<List<CharacterDTO>>
}

interface LocalCharactersDatasource {
    suspend fun fetchCharacters(): List<CharacterEntity>?
    suspend fun saveCharacter(vararg characterEntity: CharacterEntity)
}