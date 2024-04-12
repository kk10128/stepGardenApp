package edu.uark.virtualfitnessgarden

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.uark.virtualfitnessgarden.Model.PlantUser
import edu.uark.virtualfitnessgarden.Model.PlantUserRepository
import edu.uark.virtualfitnessgarden.Model.User
import edu.uark.virtualfitnessgarden.Model.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ShopActivityViewModel(private val plantUserRepository: PlantUserRepository, private val userRepository: UserRepository, private val user_id: Int) : ViewModel() {
    var curUser: LiveData<User> = userRepository.getUser(user_id).asLiveData()

    suspend fun buyFertilizerForUser(user_id: Int, price: Int): Boolean {
        if (canAfford(user_id, price)) {
            userRepository.buyFertilizer(user_id, price)
            return true
        }
        return false
    }

    suspend fun buyPlantForUser(user_id: Int, plant_id: Int, price: Int): Boolean {
        if (canAfford(user_id, price)) {
            //plantUserRepository.addPlant(user_id, plant_id, price)
            val nextId = getNextPlantUserId(user_id)
            val newPlant = PlantUser(plant_id, user_id, nextId, 0, 1)
            plantUserRepository.insert(newPlant)
            userRepository.spendCoins(user_id, price)

            return true
        }
        return false
    }

    suspend fun getNextPlantUserId(user_id: Int): Int{
        return plantUserRepository.getNextPlantUserId(user_id)
    }

    suspend fun canAfford(user_id: Int, amount: Int): Boolean {
        return userRepository.canAfford(user_id, amount)
    }

}

class ShopActivityViewModelFactory(private val plantUserRepository: PlantUserRepository, private val userRepository: UserRepository, private val user_id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShopActivityViewModel(plantUserRepository, userRepository, user_id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}