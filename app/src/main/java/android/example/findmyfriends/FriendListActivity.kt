package android.example.findmyfriends

import android.content.Intent
import android.example.findmyfriends.geoservice.getCoordinatesByLocation
import android.example.findmyfriends.mutabledata.*
import android.example.findmyfriends.retrofitservice.VkFriendsService
import android.example.findmyfriends.vkdata.friendsinfo.GetVkFriendsData
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
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

//если стереть текст из эдитвью чекбокс сохраняется относительно позиции, а не пользователя

class FriendListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gson = GsonBuilder().setLenient().create()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(VK_FRIENDS_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val intentToken : String = intent.getStringExtra(vkAccessToken).toString()
        val request = requestFriendsVK + intentToken + miscURL
        val usersWithCity : MutableList<Item> = mutableListOf()

        val editText = findViewById<EditText>(R.id.enter_user)
        val selectAllButton = findViewById<Button>(R.id.pick_all)
        val openMapButton = findViewById<FloatingActionButton>(R.id.open_map_button)

        val vkFriendsService : VkFriendsService = retrofit.create(VkFriendsService::class.java)
        vkFriendsService.getFriendList(request).enqueue(object : Callback<GetVkFriendsData> {
            override fun onResponse(call: Call<GetVkFriendsData>, response: Response<GetVkFriendsData>) {
                val result = response.body()?.response!!

                for(i in 0 until result.count!!) {
                    if(result.items?.get(i)?.city?.title != null) {
                        result.items[i].nameOfUser = result.items[i].first_name + " " + result.items[i].last_name
                        usersWithCity.add(result.items[i])
                    }
                }

                val recyclerView : RecyclerView = findViewById(R.id.list_view)
                val vkAdapter = VkFriendListAdapter(usersWithCity)
                recyclerView.layoutManager = LinearLayoutManager(this@FriendListActivity)
                recyclerView.adapter = vkAdapter

                selectAllButton.setOnClickListener {
                    vkAdapter.selectAll()
                }

                editText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val updated : MutableList<Item> = mutableListOf()
                        for(item in usersWithCity) {
                            if(item.nameOfUser.lowercase(Locale.getDefault()).contains(s.toString()
                                    .lowercase(Locale.getDefault()))) {
                                updated.add(item)
                            }

                        }
                        vkAdapter.filterList(updated)
                    }
                })

                openMapButton.setOnClickListener{
                    val list = vkAdapter.getChecked()
                    val listOfChecked = mutableListOf<Int>()
                    for(i in usersWithCity.indices) {
                        try{
                            if(list.valueAt(i)) listOfChecked.add(list.keyAt(i))
                        } catch (e: ArrayIndexOutOfBoundsException) {}
                    }

                    if(listOfChecked.isEmpty())
                        makeToast(this@FriendListActivity, "Вы ничего не выбрали!")
                    else {
                        val checkedUsers = mutableListOf<Item>()
                        for(i in 0 until listOfChecked.size) checkedUsers.add(usersWithCity[listOfChecked[i]])

                        locData = getCoordinatesByLocation(this@FriendListActivity, checkedUsers)

                        val mapIntent = Intent(this@FriendListActivity, MapsActivity::class.java)
                        startActivity(mapIntent)
                    }
                }
            }

            override fun onFailure(call: Call<GetVkFriendsData>, t: Throwable) {
                makeToast(this@FriendListActivity, "Не удалось загрузить данные.")
            }
        }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

