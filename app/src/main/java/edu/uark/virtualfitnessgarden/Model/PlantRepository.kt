package edu.uark.virtualfitnessgarden.Model

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PlantRepository(private val plantDao: PlantDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    //val allUsers: Flow<List<User>> = userDao.getAlphabetizedTasks()

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    fun getTask(plant_id:Int):Flow<Plant>{
        return plantDao.getPlant(plant_id)
    }

    fun getTaskNotLive(plant_id:Int):Plant{
        return plantDao.getPlantNotLive(plant_id)
    }

    suspend fun getPlantImageStage1(plant_id: Int): Int{
        return plantDao.getPlantImageStage1(plant_id)
    }

    suspend fun getPlantImageStage2(plant_id: Int): Int{
        return plantDao.getPlantImageStage2(plant_id)
    }

    suspend fun getPlantImageStage3(plant_id: Int): Int{
        return plantDao.getPlantImageStage3(plant_id)
    }

    suspend fun delete(plant: Plant) {
        plantDao.delete(plant)
    }



    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(plant: Plant) {
        //Note that I am pretending this is a network call by adding
        //a 5 second sleep call here
        //If you don't run this in a scope that is still active
        //Then the call won't complete
        //Thread.sleep(5000)
        Thread.sleep(2500)
        plantDao.insert(plant)

        //Log.d("DEBUG", "Added plant " + plant.plantName)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(plant: Plant) {
        plantDao.update(plant)
    }
}