package com.oskr19.breakingbad.core.data

import androidx.room.ColumnInfo
import java.sql.Timestamp
import java.util.Calendar

open class BaseEntity {
    @ColumnInfo(name = "created_at")
    var createdAt: Timestamp = Timestamp(Calendar.getInstance().time.time)
}
