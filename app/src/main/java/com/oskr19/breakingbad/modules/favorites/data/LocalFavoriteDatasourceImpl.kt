package com.oskr19.breakingbad.modules.favorites.data

class LocalFavoriteDatasourceImpl(private val dao: FavoriteDao) : LocalFavoriteDatasource {
    override fun removeAll(): Boolean {
        dao.deleteAll()
        return true
    }

    override fun setFavorite(characterId: Int, isFavorite: Boolean): Int {
        if (isFavorite) {
            dao.deleteById(characterId)
        } else {
            dao.insertAll(FavoriteEntity(characterId))
        }
        return characterId
    }

    override fun getById(characterId: Int): FavoriteEntity? {
        return dao.getById(characterId)
    }
}