package edu.uark.virtualfitnessgarden.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plantinfo_table")
class Plant (
    @PrimaryKey(autoGenerate = true) val plant_id: Int?,
    @ColumnInfo(name = "stage_1_image_id") var stage_1_image_id: Int,
    @ColumnInfo(name = "stage_2_image_id") var stage_2_image_id: Int,
    @ColumnInfo(name = "stage_3_image_id") var stage_3_image_id: Int

)