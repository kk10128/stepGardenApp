package edu.uark.virtualfitnessgarden.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plantUserinfo_table", primaryKeys = ["plant_id", "user_id", "id"])
class PlantUser (
    @ColumnInfo(name = "plant_id") val plant_id: Int,
    @ColumnInfo(name = "user_id") var user_id: Int,
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "status") var status: Int, // status of plant, 0: bad, 1: okay, 2: good
    @ColumnInfo(name = "currentStage") var currentStage: Int // current growth stage of plant from 0 - 2


)