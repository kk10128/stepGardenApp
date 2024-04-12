package edu.uark.virtualfitnessgarden.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userinfo_table")
class User (
    @PrimaryKey(autoGenerate = true) val user_id: Int?,
    @ColumnInfo(name = "userName") var userName: String,
    @ColumnInfo(name = "stepCount") var stepCount: Int,
    @ColumnInfo(name = "fertilizerCount") var fertilizerCount: Int = 0,
    @ColumnInfo(name = "coinCount") var coinCount: Int = 5000
)