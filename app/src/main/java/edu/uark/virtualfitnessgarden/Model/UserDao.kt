package edu.uark.virtualfitnessgarden.Model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    //Get a single user with a given id
    @Query("SELECT * FROM userinfo_table WHERE user_id=:user_id" )
    fun getUser(user_id:Int): Flow<User>

    @Query("SELECT * FROM userinfo_table WHERE user_id=:user_id" )
    fun getUserNotLive(user_id:Int): User

    @Query("SELECT userName FROM userinfo_table WHERE user_id=:user_id")
    fun getUserName(user_id:Int): String

    @Query("SELECT stepCount FROM userinfo_table WHERE user_id=:user_id")
    fun getUserStepCount(user_id:Int): Int

    @Query("SELECT fertilizerCount FROM userinfo_table WHERE user_id=:user_id")
    fun getUserFertilizerCount(user_id: Int): Int

    @Query("SELECT COUNT(*) FROM userinfo_table WHERE user_id =:user_id AND fertilizerCount > 0")
    fun hasFertilizer(user_id: Int): Boolean

    @Query("SELECT EXISTS (SELECT 1 FROM userinfo_table WHERE user_id=:user_id AND coinCount >= :amount LIMIT 1)")
    suspend fun canAfford(user_id: Int, amount: Int): Boolean

    @Query("UPDATE userinfo_table SET fertilizerCount = fertilizerCount - 1 WHERE user_id=:user_id")
    suspend fun decrementFertilizerCount(user_id: Int)

    @Query("UPDATE userinfo_table SET fertilizerCount = fertilizerCount + 1, coinCount = coinCount - :amount WHERE user_id=:user_id")
    suspend fun buyFertilizer(user_id: Int, amount: Int)

    @Query("UPDATE userinfo_table SET coinCount = coinCount - :amount WHERE user_id=:user_id")
    suspend fun spendCoins(user_id: Int, amount: Int)

    //Insert a single User
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    // Delete all users
    @Query("DELETE FROM userinfo_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(user: User)

    //Update a single user
    @Update
    suspend fun update(user: User):Int
}