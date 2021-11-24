package com.oskr19.breakingbad.modules.favorites.data

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun setFavorite(characterId: Int, isFavorite: Boolean): Flow<Int>
    suspend fun removeAll(): Flow<Boolean>
}