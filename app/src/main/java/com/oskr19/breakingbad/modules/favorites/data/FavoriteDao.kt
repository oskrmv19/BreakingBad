package com.oskr19.breakingbad.modules.favorites.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg characterId: FavoriteEntity)

    @Query("DELETE FROM favorites where charId = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM favorites where charId = :id")
    fun getById(id: Int): FavoriteEntity?

    @Query("DELETE FROM favorites")
    fun deleteAll()

    @Query("SELECT * FROM favorites LIMIT :limit OFFSET :offset")
    fun fetch(offset: Int, limit: Int): List<FavoriteEntity>?
}