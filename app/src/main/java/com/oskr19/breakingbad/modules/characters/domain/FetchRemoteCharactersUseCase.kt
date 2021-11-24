package com.oskr19.breakingbad.modules.characters.domain

import com.oskr19.breakingbad.core.domain.BaseUseCase
import kotlinx.coroutines.flow.Flow

class FetchRemoteCharactersUseCase(private val repository: FetchCharactersRepository):
    BaseUseCase<Flow<List<CharacterDTO>>, FetchRemoteCharactersUseCase.Params>() {

    data class Params(val offset: Int)

    override suspend fun run(params: Params) = repository.fetchRemoteCharacters(params.offset)

}