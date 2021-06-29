package android.example.findmyfriends

import android.example.findmyfriends.presenters.PresenterFriends
import android.example.findmyfriends.retrofitservice.VkFriendsService
import android.example.findmyfriends.vkdata.adapter.VkFriendListAdapter
import android.example.findmyfriends.vkdata.friendsinfo.Item
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Retrofit
import java.util.*

//если стереть текст из эдитвью чекбокс сохраняется относительно позиции, а не пользователя

class FriendListActivity : AppCompatActivity() {

    private val presenter = PresenterFriends(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editText = findViewById<EditText>(R.id.enter_user)
        val selectAllButton = findViewById<Button>(R.id.pick_all)
        val openMapButton = findViewById<FloatingActionButton>(R.id.open_map_button)

        val intentToken : String = intent.getStringExtra(presenter.accessCurrentToken()).toString()
        val request = presenter.accessRequestVK() + intentToken + presenter.accessMiscURL()
        val usersWithCity : MutableList<Item> = mutableListOf()

        val recyclerView : RecyclerView = findViewById(R.id.list_view)
        val vkAdapter = VkFriendListAdapter(usersWithCity)
        recyclerView.layoutManager = LinearLayoutManager(this@FriendListActivity)
        recyclerView.adapter = vkAdapter

        val retrofit : Retrofit = presenter.buildRetrofit()
        val vkFriendsService : VkFriendsService = retrofit.create(VkFriendsService::class.java)

        presenter.getResponse(vkFriendsService, request, usersWithCity, vkAdapter)

        selectAllButton.setOnClickListener {
            vkAdapter.selectAll()
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) {
                presenter.controlTextFlow(s, usersWithCity, vkAdapter)
            }
        })

        openMapButton.setOnClickListener{
            presenter.openMapHandler(this, vkAdapter, usersWithCity)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

