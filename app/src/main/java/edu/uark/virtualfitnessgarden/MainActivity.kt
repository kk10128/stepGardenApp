package edu.uark.virtualfitnessgarden

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uark.virtualfitnessgarden.Model.PlantRepository
import edu.uark.virtualfitnessgarden.Model.PlantUser
import edu.uark.virtualfitnessgarden.Model.User
import edu.uark.virtualfitnessgarden.Util.GridSpacingItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val user_id: Int = 0

    private val gardenGridAdapterViewModel: GardenGridAdapterViewModel by viewModels{
        GardenGridAdapterViewModelFactory((application as VirtualFitnessGardenApplication).plant_respository, (application as VirtualFitnessGardenApplication).plant_user_repository, (application as VirtualFitnessGardenApplication).user_repository, user_id)
    }

    private val mainActivityViewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((application as VirtualFitnessGardenApplication).plant_user_repository, (application as VirtualFitnessGardenApplication).user_repository, user_id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prototype_main)

        mainActivityViewModel.curUser.observe(this) { user ->
            val textView_stepCount = findViewById<TextView>(R.id.textView_stepCount)
            val textView_userNameGarden = findViewById<TextView>(R.id.textView_userNameGarden)
            val textView_userFertilizerCount = findViewById<TextView>(R.id.textView_userFertilizerCount)

            textView_stepCount.setText(user.stepCount.toString())
            textView_userNameGarden.setText("${user.userName}'s Garden")
            textView_userFertilizerCount.setText("# Fertilizers: ${user.fertilizerCount}")
        }


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //val adapter = GardenGridAdapter(plantList, this, gardenGridAdapterViewModel, user_id)
        val adapter = GardenGridAdapter(this, gardenGridAdapterViewModel, user_id)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(3, 32, false))

        mainActivityViewModel.allUserPlants.observe(this) { userPlants ->
            // Update the cached copy of the user's plants in the adapter.
            userPlants.let{
                adapter.submitList(it)
            }
        }

        initializeButtons()
    }

    fun initializeButtons(){
        val button_shop = findViewById<ImageButton>(R.id.button_shop)
        val button_home = findViewById<ImageButton>(R.id.button_home)
        val button_friend = findViewById<ImageButton>(R.id.button_friends)

        if(!ShopActivity::class.java.isAssignableFrom(this::class.java)){
            // We are not in shop activity
            button_shop.setOnClickListener{
                val intent = Intent(this, ShopActivity::class.java)
                intent.putExtra("user_id", user_id)
                finish()
                startActivity(intent)
            }
        }

        if(!MainActivity::class.java.isAssignableFrom(this::class.java)){
            // We are not in home main activity
            button_home.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("user_id", user_id)
                finish()
                startActivity(intent)
            }
        }

        if(!FriendActivity::class.java.isAssignableFrom(this::class.java)){
            // We are not in home main activity
            button_friend.setOnClickListener{
                val intent = Intent(this, FriendActivity::class.java)
                intent.putExtra("user_id", user_id)
                finish()
                startActivity(intent)
            }
        }
    }


}