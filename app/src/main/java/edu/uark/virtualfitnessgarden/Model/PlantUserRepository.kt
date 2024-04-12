package edu.uark.virtualfitnessgarden.Model

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PlantUserRepository(private val plantUserDao: PlantUserDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    fun getAllPlantUsers(user_id: Int): Flow<List<PlantUser>>{
        return plantUserDao.getAllPlantUsers(user_id)
    }

    fun getAllPlantUsersNotLive(user_id: Int): List<PlantUser>{
        return plantUserDao.getAllPlantUsersNotLive(user_id)
    }

    fun getPlantUser(user_id: Int, id:Int):Flow<PlantUser>{
        return plantUserDao.getPlantUser(user_id, id)
    }

    fun getPlantUserNotLive(user_id: Int, id:Int):PlantUser{
        return plantUserDao.getPlantUserNotLive(user_id, id)
    }

    suspend fun getNextPlantUserId(user_id: Int): Int{
        return plantUserDao.getNextPlantUserId(user_id)
    }

    fun isWatered(user_id: Int, id: Int): Boolean{
        return plantUserDao.isWatered(user_id, id)
    }

    fun isMaxStage(user_id: Int, id: Int): Boolean{
        return plantUserDao.isMaxStage(user_id, id)
    }


//    suspend fun buyPlant(user_id: Int, plant_id: Int, amount: Int){
//        plantUserDao.buyPlant(user_id, plant_id, amount)
//    }

    suspend fun delete(plantUser: PlantUser) {
        plantUserDao.delete(plantUser)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(plantUser: PlantUser) {
        //Note that I am pretending this is a network call by adding
        //a 5 second sleep call here
        //If you don't run this in a scope that is still active
        //Then the call won't complete
        //Thread.sleep(5000)

        Log.d("Database", "PlantUserRepository.insert() // Before ")

        Thread.sleep(2500)
        plantUserDao.insert(plantUser)

        Log.d("Database", "\tPlantUserRepository.insert() // After ")
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(plantUser: PlantUser) {
        plantUserDao.update(plantUser)
    }
}