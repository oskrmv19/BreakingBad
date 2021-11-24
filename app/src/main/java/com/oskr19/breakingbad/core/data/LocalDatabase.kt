package com.oskr19.breakingbad.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oskr19.breakingbad.modules.characters.data.CharacterDao
import com.oskr19.breakingbad.modules.characters.data.CharacterEntity
import com.oskr19.breakingbad.modules.favorites.data.FavoriteDao
import com.oskr19.breakingbad.modules.favorites.data.FavoriteEntity

@Database(
    version = 1, exportSchema = false,
    entities = [FavoriteEntity::class, CharacterEntity::class],
)
@TypeConverters(DataTypeConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun favoriteDao(): FavoriteDao
}