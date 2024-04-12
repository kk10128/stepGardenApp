package edu.uark.virtualfitnessgarden

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import edu.uark.virtualfitnessgarden.Model.PlantUser
import edu.uark.virtualfitnessgarden.Model.PlantUserRepository
import edu.uark.virtualfitnessgarden.Model.User
import edu.uark.virtualfitnessgarden.Model.UserRepository
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel(private val plantUserRepository: PlantUserRepository, private val userRepository: UserRepository, private val user_id: Int) : ViewModel() {
    val allUserPlants: LiveData<List<PlantUser>> = plantUserRepository.getAllPlantUsers(user_id).asLiveData()
    var curUser: LiveData<User> = userRepository.getUser(user_id).asLiveData()
}

class MainActivityViewModelFactory(private val plantUserRepository: PlantUserRepository, private val userRepository: UserRepository, private val user_id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(plantUserRepository, userRepository, user_id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}