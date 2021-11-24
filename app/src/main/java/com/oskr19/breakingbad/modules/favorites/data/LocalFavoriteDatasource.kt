package com.oskr19.breakingbad.modules.favorites.data

interface LocalFavoriteDatasource {
    fun removeAll(): Boolean
    fun setFavorite(characterId: Int, isFavorite: Boolean): Int
    fun getById(characterId: Int): FavoriteEntity?
}