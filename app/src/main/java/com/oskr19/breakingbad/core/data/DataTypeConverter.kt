package com.oskr19.breakingbad.core.data

import androidx.room.TypeConverter
import com.oskr19.breakingbad.core.domain.toArrayStringFromJson
import com.oskr19.breakingbad.core.domain.toJsonString
import java.sql.Timestamp

class DataTypeConverter {

    @TypeConverter
    fun fromString(value: String) = value.toArrayStringFromJson()

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>) = list.toJsonString()

    @TypeConverter
    fun timeStampFromString(value: String): Timestamp {
        return Timestamp(value.toLong())
    }

    @TypeConverter
    fun timeStampToString(timestamp: Timestamp): String {
        return timestamp.time.toString()
    }
}