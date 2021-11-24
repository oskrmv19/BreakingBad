package com.oskr19.breakingbad.modules.characters.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oskr19.breakingbad.core.data.BaseEntity

@Entity(tableName = "characters")
class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "char_id")
    var charId: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "birthday")
    var birthday: String,

    @ColumnInfo(name = "occupation")
    var occupation: ArrayList<String> = arrayListOf(),

    @ColumnInfo(name = "img")
    var img: String,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "nickname")
    var nickname: String,

    @ColumnInfo(name = "portrayed")
    var portrayed: String
): BaseEntity()
