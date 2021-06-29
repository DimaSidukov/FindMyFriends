package android.example.findmyfriends.presenters

import android.content.Context
import android.content.Intent
import android.example.findmyfriends.FriendListActivity
import android.example.findmyfriends.MapsActivity
import android.example.findmyfriends.geoservice.getCoordinatesByLocation
import android.example.findmyfriends.model.locData
import android.example.findmyfriends.presenters.common.PresenterBase
import android.example.findmyfriends.retrofitservice.VkFriendsService
import android.example.findmyfriends.vkdata.adapter.VkFriendListAdapter
import android.example.findmyfriends.vkdata.friendsinfo.GetVkFriendsData
import android.example.findmyfriends.vkdata.friendsinfo.Item
import android.text.Editable
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class PresenterFriends(context: Context) : PresenterBase(context) {

    fun buildRetrofit() : Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(accessBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getResponse(vkFriendsService: VkFriendsService,
                    request: String,
                    usersWithCity: MutableList<Item>,
                    adapter: VkFriendListAdapter) {
        vkFriendsService.getFriendList(request).enqueue(object : Callback<GetVkFriendsData> {
            override fun onResponse(call: Call<GetVkFriendsData>, response: Response<GetVkFriendsData>) {
                val result = response.body()?.response!!

                for(i in 0 until result.count!!) {
                    if(result.items?.get(i)?.city?.title != null) {
                        result.items[i].nameOfUser = result.items[i].first_name + " " + result.items[i].last_name
                        usersWithCity.add(result.items[i])
                    }
                }

                adapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<GetVkFriendsData>, t: Throwable) {
                makeToast("Не удалось загрузить данные")
            }
        })

    }

    fun controlTextFlow(s: Editable?, list: MutableList<Item>, adapter: VkFriendListAdapter) {
        val updated : MutableList<Item> = mutableListOf()
        for(item in list) {
            if(item.nameOfUser.lowercase(Locale.getDefault()).contains(s.toString().lowercase(Locale.getDefault())))
                updated.add(item)
        }
        adapter.filterList(updated)
    }

    fun openMapHandler(viewActivity: FriendListActivity, adapter: VkFriendListAdapter, listInput: MutableList<Item>) {
        val list = adapter.getChecked()
        val listOfChecked = mutableListOf<Int>()

        for(i in listInput.indices) {
            try {
                if(list.valueAt(i)) listOfChecked.add(list.keyAt(i))
            } catch (e: ArrayIndexOutOfBoundsException) { }
        }

        if(listOfChecked.isEmpty())
            makeToast("Вы ничего не выбрали!")
        else {
            val checkedUsers = mutableListOf<Item>()
            for(i in 0 until listOfChecked.size) checkedUsers.add(listInput[listOfChecked[i]])

            locData = getCoordinatesByLocation(viewActivity, checkedUsers)

            val mapIntent = Intent(viewActivity, MapsActivity::class.java)
            viewActivity.startActivity(mapIntent)
        }
    }
}