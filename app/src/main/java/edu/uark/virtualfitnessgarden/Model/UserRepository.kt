package edu.uark.virtualfitnessgarden.Model

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class UserRepository(private val userDao: UserDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    //val allUsers: Flow<List<User>> = userDao.getOrderedUsers()

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    fun getUser(user_id:Int):Flow<User>{
        val user = userDao.getUser(user_id)
        Log.d("DEBUG","UserRepository.getUser() // user: $user")

        if(user == null){
            Log.d("DEBUG","UserRepository.getUser() // is NULL")
        } else {
            Log.d("DEBUG","UserRepository.getUser() // is NOT NULL")
        }

        return userDao.getUser(user_id)
    }

    fun getUserNotLive(user_id:Int):User{
        return userDao.getUserNotLive(user_id)
    }

    fun getUserName(user_id:Int): String{
        return userDao.getUserName(user_id)
    }

    fun getUserStepCount(user_id: Int): Int{
        return userDao.getUserStepCount(user_id)
    }

    fun getUserFertilizerCount(user_id: Int): Int{
        return userDao.getUserFertilizerCount(user_id)
    }

    fun hasFertilizer(user_id: Int): Boolean{
        return userDao.hasFertilizer(user_id)
    }

    suspend fun canAfford(user_id: Int, amount: Int): Boolean{
        return userDao.canAfford(user_id, amount)
    }

    suspend fun decrementFertilizerCount(user_id: Int){
        userDao.decrementFertilizerCount(user_id)
    }

    suspend fun spendCoins(user_id: Int, amount: Int){
        userDao.spendCoins(user_id, amount)
    }

    suspend fun buyFertilizer(user_id: Int, amount: Int){
        userDao.buyFertilizer(user_id, amount)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }



    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        //Note that I am pretending this is a network call by adding
        //a 5 second sleep call here
        //If you don't run this in a scope that is still active
        //Then the call won't complete
        //Thread.sleep(5000)

        Log.d("Database", "UserRepository.insert() // Before ")

        Thread.sleep(2500)
        userDao.insert(user)

        Log.d("Database", "\tUserRepository.insert() // Before ")

        //Log.d("DEBUG", "Added user " + user.userName)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(user: User) {
        userDao.update(user)
    }
}