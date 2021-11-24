package com.oskr19.breakingbad.modules.favorites.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oskr19.breakingbad.core.data.BaseEntity

@Entity(tableName = "favorites")
class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    var charId: Int = 0,

): BaseEntity()