package com.oskr19.breakingbad.modules.characters.data

import com.oskr19.breakingbad.core.data.RemoteDatasource
import com.oskr19.breakingbad.modules.characters.domain.CharacterDTO
import com.oskr19.breakingbad.modules.characters.domain.FetchCharactersRepository
import com.oskr19.breakingbad.modules.favorites.data.LocalFavoriteDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

class FetchCharactersRepositoryImpl(
    private val mapper: CharacterMapper,
    private val localCharactersDatasource: LocalCharactersDatasource,
    private val remoteRemoteCharactersDatasource: RemoteCharactersDatasource,
    private val localFavoriteDatasource: LocalFavoriteDatasource
) : RemoteDatasource(), FetchCharactersRepository {

    override suspend fun fetchRemoteCharacters(offset: Int): Flow<List<CharacterDTO>> =
        remoteRemoteCharactersDatasource.fetchCharacters(offset).onEach {
            localCharactersDatasource.saveCharacter(*mapper.mapToList(it).toTypedArray())
            setFavorites(it)
        }

    override suspend fun fetchLocalCharacters(): Flow<List<CharacterDTO>> =
        flowOf(mapper.mapFromList(localCharactersDatasource.fetchCharacters())).onEach(::setFavorites)

    private fun setFavorites(list: List<CharacterDTO>) {
        list.map { character ->
            character.isFavorite = localFavoriteDatasource.getById(character.charId) != null
        }
    }
}