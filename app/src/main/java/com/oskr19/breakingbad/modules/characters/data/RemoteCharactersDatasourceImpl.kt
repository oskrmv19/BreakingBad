package com.oskr19.breakingbad.modules.characters.data

import com.oskr19.breakingbad.core.Constants.FETCH_LIMIT
import com.oskr19.breakingbad.core.data.RemoteDatasource
import com.oskr19.breakingbad.modules.characters.domain.FetchCharactersRequest

class RemoteCharactersDatasourceImpl(private val api: CharactersAPI) : RemoteDatasource(), RemoteCharactersDatasource {
    override suspend fun fetchCharacters(offset: Int) =
        getResult { api.fetch(FetchCharactersRequest.buildFetchRequest(offset, FETCH_LIMIT)) }
}
