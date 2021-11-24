package com.oskr19.breakingbad.modules.favorites.data

import com.oskr19.breakingbad.core.domain.BaseUseCase
import kotlinx.coroutines.flow.Flow

class SetFavoriteUseCase(private val repository: FavoriteRepository) :
    BaseUseCase<Flow<Int>, SetFavoriteUseCase.Params>() {

    data class Params(val characterId: Int, val isFavorite: Boolean)

    override suspend fun run(params: Params): Flow<Int> {
        return repository.setFavorite(params.characterId, params.isFavorite)
    }
}