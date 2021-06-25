package android.example.findmyfriends

import android.example.findmyfriends.mutabledata.*
import android.example.findmyfriends.retrofitservice.VkFriendsService
import android.example.findmyfriends.vkdata.friendsinfo.GetVkFriendsData
import android.example.findmyfriends.vkdata.friendsinfo.Item
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.widget.Button
import android.widget.Toast
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
import java.lang.Exception
import java.util.*


//при поиске в эдитвью онлайн обновление списка
//появление кнопки, если хоть один флажок checked - не сделано пока
//если выбрать всё, а затем отщёлкивать, то элементы вне экрана выбираются заново


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

                val recyclerView: RecyclerView = findViewById(R.id.recycler_view_list)
                recyclerView.layoutManager = LinearLayoutManager(this@FriendListActivity)
                val vkAdapter = VkFriendListAdapter(usersWithCity)
                recyclerView.adapter = vkAdapter

                selectAllButton.setOnClickListener {
                    vkAdapter.selectAll()
                }

                openMapButton.setOnClickListener{
                    val list = vkAdapter.getChecked()
                    val listOfChecked = mutableListOf<Int>()
                    for(i in usersWithCity.indices) {
                        try{
                            if(list.valueAt(i)) listOfChecked.add(list.keyAt(i))
                        } catch (e: ArrayIndexOutOfBoundsException) {}
                    }

                    if(listOfChecked.isEmpty())
                        Toast.makeText(this@FriendListActivity, "Вы ничего не выбрали!", Toast.LENGTH_SHORT).show()
                    else Log.d("LIST OF", listOfChecked.toString())
                }
            }

            override fun onFailure(call: Call<GetVkFriendsData>, t: Throwable) {
                Toast.makeText(this@FriendListActivity, "Не удалось загрузить данные.", Toast.LENGTH_SHORT).show()
            }
        }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

