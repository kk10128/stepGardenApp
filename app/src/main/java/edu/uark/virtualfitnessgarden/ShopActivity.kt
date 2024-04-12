package edu.uark.virtualfitnessgarden

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import edu.uark.virtualfitnessgarden.Model.ShopItem
import edu.uark.virtualfitnessgarden.Util.ShopItemAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopActivity : AppCompatActivity() {

    private lateinit var plantShopItemAdapter: ShopItemAdapter
    private lateinit var fertilizerShopItemAdapter: ShopItemAdapter

    val user_id: Int = 0

    private val shopActivityViewModel: ShopActivityViewModel by viewModels {
        ShopActivityViewModelFactory((application as VirtualFitnessGardenApplication).plant_user_repository, (application as VirtualFitnessGardenApplication).user_repository, user_id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prototype_shop)

        shopActivityViewModel.curUser.observe(this) { user ->
            val textView_coinCount = findViewById<TextView>(R.id.textView_coinCount)

            textView_coinCount.setText("Coins: ${user.coinCount}")
        }

        initializeButtons()

        initializePlantShop()
        initializeFertilizerShop()
    }
    
    fun initializePlantShop(){
        val recyclerView_plantShop = findViewById<RecyclerView>(R.id.recyclerView_plantShop)

        // Initialize your adapter
        plantShopItemAdapter = ShopItemAdapter { position ->
            // Handle item click here
            val clickedItem: ShopItem = plantShopItemAdapter.getItemAtPosition(position)

            val builder = AlertDialog.Builder(this)

            builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to buy?")
                .setPositiveButton("Yes") { dialog, which ->
                    // Handle the positive button click (user confirmed)

                    buyPlant(user_id, clickedItem.plant_id, clickedItem.price)

                    dialog.dismiss() // You can dismiss the dialog here or perform additional actions
                }
                .setNegativeButton("No") { dialog, which ->
                    // Handle the negative button click (user canceled)
                    dialog.dismiss() // You can dismiss the dialog here or perform additional actions
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()

            //Toast.makeText(this, "Clicked on ShopItem with price ${clickedItem.price}", Toast.LENGTH_SHORT).show()
        }

        recyclerView_plantShop.adapter = plantShopItemAdapter

        val dividerItemDecoration = DividerItemDecoration(recyclerView_plantShop.getContext(), DividerItemDecoration.HORIZONTAL)
        recyclerView_plantShop.addItemDecoration(dividerItemDecoration)

        var newShopItem = ShopItem(R.drawable.img_plant_rose_3, 7500, isPlant = true, plant_id = R.integer.plant_rose_id)
        plantShopItemAdapter.addShopItem(newShopItem)

        newShopItem = ShopItem(R.drawable.img_plant_tulip_3, 3000, isPlant = true, plant_id = R.integer.plant_tulip_id)
        plantShopItemAdapter.addShopItem(newShopItem)

        newShopItem = ShopItem(R.drawable.img_plant_cactus_3, 1000, isPlant = true, plant_id = R.integer.plant_cactus_id)
        plantShopItemAdapter.addShopItem(newShopItem)
    }

    private fun showCannotAffordDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Insufficient Funds")
            .setMessage("You cannot afford this purchase.")
            .setPositiveButton("OK") { dialog, which ->
                // Handle the positive button click (dismiss the dialog)
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun initializeFertilizerShop(){
        val recyclerView_fertilizerShop = findViewById<RecyclerView>(R.id.recyclerView_fertilizerShop)

        // Initialize your adapter
        fertilizerShopItemAdapter = ShopItemAdapter { position ->
            // Handle item click here
            val clickedItem: ShopItem = fertilizerShopItemAdapter.getItemAtPosition(position)


            val builder = AlertDialog.Builder(this)

            builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to buy?")
                .setPositiveButton("Yes") { dialog, which ->
                    // Handle the positive button click (user confirmed)

                    buyFertilizer(clickedItem.price)

                    dialog.dismiss() // You can dismiss the dialog here or perform additional actions
                }
                .setNegativeButton("No") { dialog, which ->
                    // Handle the negative button click (user canceled)
                    dialog.dismiss() // You can dismiss the dialog here or perform additional actions
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()

            //Toast.makeText(this, "Clicked on ShopItem with price ${clickedItem.price}", Toast.LENGTH_SHORT).show()
        }

        recyclerView_fertilizerShop.adapter = fertilizerShopItemAdapter

        var newShopItem = ShopItem(R.drawable.img_fertilizer, 1000, isFertilizer = true)
        fertilizerShopItemAdapter.addShopItem(newShopItem)

    }

    fun buyPlant(user_id: Int, plant_id: Int, price: Int){
        CoroutineScope(Dispatchers.Main).launch {
            if (!shopActivityViewModel.buyPlantForUser(user_id, plant_id, price)) {
                showCannotAffordDialog()
            }
        }
    }

    fun buyFertilizer(price: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            if (!shopActivityViewModel.buyFertilizerForUser(user_id, price)) {
                showCannotAffordDialog()
            }
        }
    }

    fun initializeButtons(){
        val button_shop = findViewById<ImageButton>(R.id.button_shop)
        val button_home = findViewById<ImageButton>(R.id.button_home)
        val button_friend = findViewById<ImageButton>(R.id.button_friends)

        if(!ShopActivity::class.java.isAssignableFrom(this::class.java)){
            // We are not in shop activity
            button_shop.setOnClickListener{
                val intent = Intent(this, ShopActivity::class.java)
                finish()
                startActivity(intent)
            }
        }

        if(!MainActivity::class.java.isAssignableFrom(this::class.java)){
            // We are not in home main activity
            button_home.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
        }

        if(!FriendActivity::class.java.isAssignableFrom(this::class.java)){
            // We are not in home main activity
            button_friend.setOnClickListener{
                val intent = Intent(this, FriendActivity::class.java)
                finish()
                startActivity(intent)
            }
        }
    }

}