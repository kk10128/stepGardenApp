package edu.uark.virtualfitnessgarden

import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import edu.uark.virtualfitnessgarden.Model.Friend
import edu.uark.virtualfitnessgarden.Util.FriendListItemAdapter


class FriendActivity : AppCompatActivity() {

    private lateinit var friendListItemAdapter: FriendListItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prototype_friends)

        initializeButtons()

        friendListItemAdapter = FriendListItemAdapter {position ->
            // Handle item click here
            val clickedFriend: Friend = friendListItemAdapter.getItemAtPosition(position)
        }

        var recyclerView_friendList = findViewById<RecyclerView>(R.id.recyclerView_friendList)
        recyclerView_friendList.adapter = friendListItemAdapter

        val dividerItemDecoration = DividerItemDecoration(recyclerView_friendList.getContext(), DividerItemDecoration.VERTICAL)
        recyclerView_friendList.addItemDecoration(dividerItemDecoration)

        var newFriend = Friend("Hector", "hsal_o", 6241, false)
        friendListItemAdapter.addFriend(newFriend)

        newFriend = Friend("Jane", "jane_doe", 1527, true)
        friendListItemAdapter.addFriend(newFriend)

        newFriend = Friend("John", "jdoe01", 23, false)
        friendListItemAdapter.addFriend(newFriend)

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