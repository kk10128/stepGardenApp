package edu.uark.virtualfitnessgarden

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.uark.virtualfitnessgarden.Model.PlantUser
import edu.uark.virtualfitnessgarden.Model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GardenGridAdapter(
    private val context: Context,
    private val gardenGridAdapterViewModel: GardenGridAdapterViewModel,
    private val user_id: Int

) : ListAdapter<PlantUser, GardenGridAdapter.ViewHolder>(PlantUserComparator()) {
//) : RecyclerView.Adapter<GardenGridAdapter.ViewHolder>() {

    // Override getItem method to fix any potential issues

    // ViewHolder class
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize views for the ViewHolder
        val progbarStatus: ProgressBar = itemView.findViewById(R.id.progbar_status)
        val imageviewPlant: ImageView = itemView.findViewById(R.id.imageview_plant)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout and create the ViewHolder
        val view = LayoutInflater.from(context).inflate(R.layout.plant_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to the ViewHolder components
        //val plant = plantList[position]
        val plant = getItem(position)

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("DEBUG", "<<<<<<<<<<<< plant.currentStage: " + plant.currentStage)

            val imageId = when (plant.currentStage){
                1 -> gardenGridAdapterViewModel.getPlantImageStage1(plant.plant_id)
                2 -> gardenGridAdapterViewModel.getPlantImageStage2(plant.plant_id)
                3 -> gardenGridAdapterViewModel.getPlantImageStage3(plant.plant_id)
                else -> R.drawable.img_plant_default_1
            }

            holder.imageviewPlant.setImageResource(imageId)
        }

        // Set progress for progbarStatus
        holder.progbarStatus.progress = plant.status

        holder.itemView.tag = plant.id
        holder.itemView.setOnLongClickListener { view ->
            // Create a PopupMenu
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.cardview_actions_menu, popupMenu.menu)

            // Set a listener for menu item clicks
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_water -> {
                        // Handle Water action
                        CoroutineScope(Dispatchers.Main).launch {
                            val isWatered = withContext(Dispatchers.IO) {
                                gardenGridAdapterViewModel.isWatered(user_id, plant.id)
                            }

                            if(!isWatered){
                                plant.status += 1
                                gardenGridAdapterViewModel.updatePlantUser(plant)
                                Toast.makeText(view.context, "Water action selected", Toast.LENGTH_SHORT).show()
                            } else {
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("Cannot fulfill request")
                                builder.setMessage("Plant is already watered")
                                builder.setPositiveButton("OK") { dialog, _ ->
                                    // Handle OK button click if needed
                                    dialog.dismiss()
                                }

                                val dialog = builder.create()
                                dialog.show()
                            }
                        }


                        true
                    }
                    R.id.action_fertilize -> {
                        // Handle Fertilize action

                        // If user doesnt have fertilizer
                        CoroutineScope(Dispatchers.Main).launch {
                            val hasFertilizer = withContext(Dispatchers.IO) {
                                gardenGridAdapterViewModel.hasFertilizer(user_id)
                            }

                            val isWatered = withContext(Dispatchers.IO) {
                                gardenGridAdapterViewModel.isWatered(user_id, plant.id)
                            }

                            val isMaxStage = withContext(Dispatchers.IO) {
                                gardenGridAdapterViewModel.isMaxStage(user_id, plant.id)
                            }

                            Log.i("DEBUG", "//////// plant.status: ${plant.status}")
                            Log.i("DEBUG", "//////// plant.currentStage: ${plant.currentStage}")

                            if(isMaxStage) {
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("Cannot fulfill request")
                                builder.setMessage("Plant is already at max stage.")
                                builder.setPositiveButton("OK") { dialog, _ ->
                                    // Handle OK button click if needed
                                    dialog.dismiss()
                                }

                                val dialog = builder.create()
                                dialog.show()

                                return@launch
                            } else if(!isWatered) {
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("Cannot fulfill request")
                                builder.setMessage("Plant must be watered further.")
                                builder.setPositiveButton("OK") { dialog, _ ->
                                    // Handle OK button click if needed
                                    dialog.dismiss()
                                }

                                val dialog = builder.create()
                                dialog.show()

                                return@launch
                            } else if(!hasFertilizer) {
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("Cannot fulfill request")
                                builder.setMessage("You must purchase fertilizer to use fertilize this plant.")
                                builder.setPositiveButton("OK") { dialog, _ ->
                                    // Handle OK button click if needed
                                    dialog.dismiss()
                                }

                                val dialog = builder.create()
                                dialog.show()

                                return@launch
                            } else {
                                // Good to fertlize now
                                gardenGridAdapterViewModel.fertilizePlant(plant, user_id)

                                Toast.makeText(
                                    view.context,
                                    "Fertilize action selected",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }


                        true
                    }
                    R.id.action_delete -> {
                        // Handle Delete action
                        gardenGridAdapterViewModel.delete(plant)

                        Toast.makeText(view.context, "Delete action selected", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }

            // Show the PopupMenu
            popupMenu.show()

            true // Return true to consume the long click event
        }


    }

//    override fun getItemCount(): Int {
//        return plantList.size
//    }

    class PlantUserComparator : DiffUtil.ItemCallback<PlantUser>() {
        override fun areItemsTheSame(oldItem: PlantUser, newItem: PlantUser): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PlantUser, newItem: PlantUser): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
