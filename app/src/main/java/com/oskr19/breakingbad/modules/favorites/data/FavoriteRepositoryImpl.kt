package com.oskr19.breakingbad.modules.favorites.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FavoriteRepositoryImpl(
    private val localFavoriteDatasource: LocalFavoriteDatasource
) : FavoriteRepository {
    override suspend fun setFavorite(characterId: Int, isFavorite: Boolean): Flow<Int> =
        flowOf(localFavoriteDatasource.setFavorite(characterId, isFavorite))

    override suspend fun removeAll(): Flow<Boolean> =
        flowOf(localFavoriteDatasource.removeAll())
}