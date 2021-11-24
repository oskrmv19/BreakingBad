package com.oskr19.breakingbad.modules.characters.domain

import com.oskr19.breakingbad.core.domain.BaseUseCase
import kotlinx.coroutines.flow.Flow

class FetchLocalCharactersUseCase(private val repository: FetchCharactersRepository):
    BaseUseCase<Flow<List<CharacterDTO>>, FetchLocalCharactersUseCase.Params>() {

    data class Params(val any: Any)

    override suspend fun run(params: Params) = repository.fetchLocalCharacters()

}