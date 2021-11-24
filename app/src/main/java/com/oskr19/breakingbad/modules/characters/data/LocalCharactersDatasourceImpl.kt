package com.oskr19.breakingbad.modules.characters.data

class LocalCharactersDatasourceImpl(private val dao: CharacterDao): LocalCharactersDatasource {
    override suspend fun fetchCharacters() = dao.fetch()
    override suspend fun saveCharacter(vararg characterEntity: CharacterEntity) {
        dao.insertAll(*characterEntity)
    }
}