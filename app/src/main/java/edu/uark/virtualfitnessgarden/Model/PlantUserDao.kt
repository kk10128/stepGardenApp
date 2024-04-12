package edu.uark.virtualfitnessgarden.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantUserDao {
    //Get a single user with a given id
    @Query("SELECT * FROM plantUserinfo_table WHERE user_id=:user_id ORDER BY id")
    fun getAllPlantUsers(user_id:Int): Flow<List<PlantUser>>

    @Query("SELECT * FROM plantUserinfo_table WHERE user_id=:user_id ORDER BY id")
    fun getAllPlantUsersNotLive(user_id:Int): List<PlantUser>

    @Query("SELECT * FROM plantUserinfo_table WHERE user_id=:user_id AND id=:id")
    fun getPlantUser(user_id: Int, id: Int): Flow<PlantUser>

    @Query("SELECT * FROM plantUserinfo_table WHERE user_id=:user_id AND id=:id")
    fun getPlantUserNotLive(user_id: Int, id: Int): PlantUser

    @Query("SELECT COALESCE(MAX(id), 0) + 1 AS next_id FROM plantUserinfo_table WHERE user_id = :user_id")
    suspend fun getNextPlantUserId(user_id: Int): Int

    @Query("SELECT COUNT(*) FROM plantUserinfo_table WHERE user_id = :user_id AND id = :id AND status >= 2")
    fun isWatered(user_id: Int, id: Int): Boolean

    @Query("SELECT COUNT(*) FROM plantUserinfo_table WHERE user_id = :user_id AND id = :id AND currentStage >= 3")
    fun isMaxStage(user_id: Int, id: Int): Boolean

    //Insert a single User
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(plantUser: PlantUser)

    // Delete all users
    @Query("DELETE FROM plantUserinfo_table")
    suspend fun deleteAll()

//    @Query("INSERT INTO plantUserinfo_table (user_id, plant_id, id)\n" +
//            "VALUES (:user_id, :plant_id, (SELECT COALESCE(MAX(id), 0) + 1 FROM plantUserinfo_table WHERE user_id = :user_id));\n")
//    suspend fun buyFertilizer(user_id: Int, amount: Int)

    @Delete
    suspend fun delete(plantUser: PlantUser)

    @Update
    suspend fun update(plantUser: PlantUser):Int
}