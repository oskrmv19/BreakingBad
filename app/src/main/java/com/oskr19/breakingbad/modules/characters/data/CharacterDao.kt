package com.oskr19.breakingbad.modules.characters.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg character: CharacterEntity)

    @Query("SELECT * FROM characters where char_id = :id")
    suspend fun getById(id: String): CharacterEntity?

    @Query("DELETE FROM characters")
    suspend fun deleteAll()

    @Query("SELECT * FROM characters")
    suspend fun fetch(): List<CharacterEntity>
}