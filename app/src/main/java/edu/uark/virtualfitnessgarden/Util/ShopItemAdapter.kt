package edu.uark.virtualfitnessgarden.Util
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uark.virtualfitnessgarden.Model.ShopItem
import edu.uark.virtualfitnessgarden.R

class ShopItemAdapter(private val onItemClick: (position: Int) -> Unit) : RecyclerView.Adapter<ShopItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize views for the ViewHolder
        val imageImageView: ImageView = itemView.findViewById(R.id.itemImageView)
        val priceTextView: TextView = itemView.findViewById(R.id.itemPriceTextView)

    }

    private val items: MutableList<ShopItem> = mutableListOf()


    fun getItemAtPosition(position: Int): ShopItem {
        return items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind your data to the views in the CardViewHolder
        val currentItem = items[position]

        // Set your image and text data to the corresponding views
        holder.imageImageView.setImageResource(currentItem.imageResource)
        holder.priceTextView.text = "Price: " + currentItem.price + " coins"

        // Set click listener for the CardView item
        holder.itemView.setOnClickListener { onItemClick.invoke(position) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addShopItem(customItem: ShopItem) {
        items.add(customItem)
        notifyItemInserted(items.size - 1)
    }


}
