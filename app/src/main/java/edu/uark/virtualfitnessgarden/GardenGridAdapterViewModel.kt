package edu.uark.virtualfitnessgarden

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.uark.virtualfitnessgarden.Model.Plant
import edu.uark.virtualfitnessgarden.Model.PlantRepository
import edu.uark.virtualfitnessgarden.Model.PlantUser
import edu.uark.virtualfitnessgarden.Model.PlantUserRepository
import edu.uark.virtualfitnessgarden.Model.User
import edu.uark.virtualfitnessgarden.Model.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GardenGridAdapterViewModel(private val plantRepository: PlantRepository, private val plantUserRepository: PlantUserRepository, private val userRepository: UserRepository, private val user_id: Int) : ViewModel() {

    val allUserPlants: LiveData<List<PlantUser>> = plantUserRepository.getAllPlantUsers(user_id).asLiveData()

    suspend fun getPlantImageStage1(plant_id: Int): Int {
        return plantRepository.getPlantImageStage1(plant_id)
    }

    suspend fun getPlantImageStage2(plant_id: Int): Int {
        return plantRepository.getPlantImageStage2(plant_id)
    }

    suspend fun getPlantImageStage3(plant_id: Int): Int {
        return plantRepository.getPlantImageStage3(plant_id)
    }

    fun hasFertilizer(user_id: Int): Boolean{
        return userRepository.hasFertilizer(user_id)
    }

    fun isWatered(user_id: Int, id: Int): Boolean{
        return plantUserRepository.isWatered(user_id, id)
    }

    fun isMaxStage(user_id: Int, id: Int): Boolean{
        return plantUserRepository.isMaxStage(user_id, id)
    }

    fun fertilizePlant(plantUser: PlantUser, user_id: Int){
//        viewModelScope.launch {
//            var user = userRepository.getUserNotLive(user_id)
//            user.fertilizerCount -= 1
//            updateUser(user)
//        }

        viewModelScope.launch {
            userRepository.decrementFertilizerCount(user_id)
        }

        plantUser.currentStage += 1
        plantUser.status = 0
        updatePlantUser(plantUser)
    }

    fun updatePlantUser(plantUser: PlantUser) {
        viewModelScope.launch {
            plantUserRepository.update(plantUser)
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch {
            userRepository.update(user)
        }
    }

    fun delete(plantUser: PlantUser) {
        viewModelScope.launch {
            plantUserRepository.delete(plantUser)
        }
    }

}

class GardenGridAdapterViewModelFactory(private val plantRepository: PlantRepository, private val plantUserRepository: PlantUserRepository, private val userRepository: UserRepository, private val user_id:Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GardenGridAdapterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GardenGridAdapterViewModel(plantRepository, plantUserRepository, userRepository, user_id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}