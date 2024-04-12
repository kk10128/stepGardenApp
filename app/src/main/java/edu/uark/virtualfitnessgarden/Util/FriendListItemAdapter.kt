package edu.uark.virtualfitnessgarden.Util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uark.virtualfitnessgarden.Model.Friend
import edu.uark.virtualfitnessgarden.R
import androidx.core.content.ContextCompat

class FriendListItemAdapter(private val onItemClick: (position: Int) -> Unit) : RecyclerView.Adapter<FriendListItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize views for the ViewHolder
        val imageView_status: ImageView = itemView.findViewById(R.id.imageView_status)
        val textView_nickname: TextView = itemView.findViewById(R.id.textView_friendNickname)
        val textView_username: TextView = itemView.findViewById(R.id.textView_friendUserName)
        val textView_stepCount: TextView = itemView.findViewById(R.id.textView_stepCount)
    }

    private val items: MutableList<Friend> = mutableListOf()


    fun getItemAtPosition(position: Int): Friend {
        return items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind your data to the views in the CardViewHolder
        val curFriend = items[position]

        // Setting views
        if(curFriend.isOnline){
            holder.imageView_status.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.green_500))
        } else {
            holder.imageView_status.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.gray_500))
        }

        holder.textView_nickname.text = curFriend.nickname
        holder.textView_username.text = "@${curFriend.username}"
        holder.textView_stepCount.text = (curFriend.stepCount).toString()

        // Set click listener for the CardView item
        //holder.itemView.setOnClickListener { onItemClick.invoke(position) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addFriend(newFriend: Friend) {
        items.add(newFriend)
        notifyItemInserted(items.size - 1)
    }

}