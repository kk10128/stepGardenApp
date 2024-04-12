package edu.uark.virtualfitnessgarden.Model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    //Get a single user with a given id
    @Query("SELECT * FROM plantinfo_table WHERE plant_id=:plant_id" )
    fun getPlant(plant_id:Int): Flow<Plant>

    @Query("SELECT * FROM plantinfo_table WHERE plant_id=:plant_id" )
    fun getPlantNotLive(plant_id:Int): Plant

    @Query("SELECT stage_1_image_id FROM plantinfo_table WHERE plant_id=:plant_id")
    suspend fun getPlantImageStage1(plant_id: Int): Int

    @Query("SELECT stage_2_image_id FROM plantinfo_table WHERE plant_id=:plant_id")
    suspend fun getPlantImageStage2(plant_id: Int): Int

    @Query("SELECT stage_3_image_id FROM plantinfo_table WHERE plant_id=:plant_id")
    suspend fun getPlantImageStage3(plant_id: Int): Int

    //Insert a single User
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(plant: Plant)

    // Delete all users
    @Query("DELETE FROM plantinfo_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(plant: Plant)

    //Update a single task
    @Update
    suspend fun update(plant: Plant):Int
}