package android.example.findmyfriends.viewmodel.friendspresenter

import android.content.Context
import android.example.findmyfriends.model.local.array
import android.example.findmyfriends.model.local.isDbCreated
import android.example.findmyfriends.model.local.locData
import android.example.findmyfriends.model.local.textViewText
import android.example.findmyfriends.model.remote.database.dao.UserInfoDao
import android.example.findmyfriends.model.remote.database.entity.UserInfo
import android.example.findmyfriends.model.remote.geodata.UserLocationData
import android.example.findmyfriends.model.remote.vk.friendsinfo.GetVkFriendsData
import android.example.findmyfriends.model.remote.vk.retrofitservice.VkFriendsService
import android.example.findmyfriends.viewmodel.common.BasePresenter
import android.example.findmyfriends.viewmodel.friendspresenter.friendsadapter.VkFriendListAdapter
import android.example.findmyfriends.viewmodel.friendspresenter.moxyinterfaces.FriendsActivityView
import android.location.Geocoder
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class FriendsPresenter @Inject constructor() : MvpPresenter<FriendsActivityView>(), BasePresenter {

    fun onViewAttach() {
        super.onFirstViewAttach()

        viewState.showText(textViewText)
        viewState.setButtonState()
    }

    fun onResponse(db: UserInfoDao?, vkFriendsService: VkFriendsService,
                   request: String, adapter: VkFriendListAdapter) : Boolean {
        var response = true
        vkFriendsService.getFriendList(request).enqueue(object : Callback<GetVkFriendsData> {
            override fun onResponse(call: Call<GetVkFriendsData>, response: Response<GetVkFriendsData>) {

                val result = response.body()?.response!!

                for(i in 0 until result.count!!) {
                    if(result.items?.get(i)?.city?.title != null) {
                        val nameOfUser = result.items[i].first_name + " " + result.items[i].last_name
                        val user = UserInfo(id = result.items[i].id!!, name = nameOfUser,
                            city = result.items[i].city?.title!!, photo_100 = result.items[i].photo_100!!)

                        GlobalScope.launch(Dispatchers.IO) {
                            db?.insertUserInfo(user)
                        }
                    }
                }
                isDbCreated = true
            }
            override fun onFailure(call: Call<GetVkFriendsData>, t: Throwable) {
                response = false
            }
        })

        adapter.notifyDataSetChanged()
        return response
    }

    fun setArrayOfChecked(vkAdapter: VkFriendListAdapter) {
        array = vkAdapter.getChecked()
    }

    fun setChecksBeforeDestruction(vkAdapter: VkFriendListAdapter) {

        try {
            vkAdapter.setChecked(array)
            vkAdapter.notifyDataSetChanged()
        } catch (e: Exception) { }
    }

    fun getUsers(users: UserInfoDao?) : List<UserInfo> {
        val userList = mutableListOf<UserInfo>()
        GlobalScope.launch {
            userList.clear()
            userList.addAll(users?.getAllUsers()!!)
        }

        return userList
    }

    fun getText() = textViewText

    fun selectAll(vkAdapter: VkFriendListAdapter) = vkAdapter.selectAll()

    fun controlTextFlow(s: Editable?, adapter: VkFriendListAdapter) {

        val initialList = adapter.getList()
        val updated : MutableList<UserInfo> = mutableListOf()
        for(item in initialList) {
            if(item.name.lowercase().startsWith(s.toString().lowercase())) {
                updated.add(item)
            }
        }
        adapter.filterList(updated)
    }

    fun openMapHandler(viewActivity: AppCompatActivity, adapter: VkFriendListAdapter) {

        val (listOfChecked, initialList) = getExclusiveIndices(adapter)
        if (listOfChecked.isNotEmpty()) {
            val checkedUsers = mutableListOf<UserInfo>()
            for (i in 0 until listOfChecked.size) checkedUsers.add(initialList[listOfChecked[i]])

            locData = getCoordinatesByLocation(viewActivity, checkedUsers)
        }
    }

    fun getExclusiveIndices(adapter: VkFriendListAdapter): Pair<MutableList<Int>, List<UserInfo>> {

        val list = adapter.getChecked()
        val initialList: List<UserInfo> = adapter.getList()
        val listOfChecked = mutableListOf<Int>()

        for(i in initialList.indices) {
            try {
                if(list.valueAt(i)) listOfChecked.add(list.keyAt(i))
            } catch (e: ArrayIndexOutOfBoundsException) { }
        }

        return Pair(listOfChecked, initialList)
    }

    private fun getCoordinatesByLocation(context: Context, users: List<UserInfo>): MutableList<UserLocationData> {

        val geocoder = Geocoder(context, Locale.getDefault())
        val listOfLocations = mutableListOf<UserLocationData>()

        val repetitiveCities = mutableListOf<String>()

        for (user in users) {
            if (!repetitiveCities.contains(user.city)) {
                val result = geocoder.getFromLocationName(user.city, 1)
                try {
                    listOfLocations.add(
                        UserLocationData(
                            result[0].latitude,
                            result[0].longitude,
                            user.name,
                            user.city
                        )
                    )
                } catch (e: Exception) { }
                repetitiveCities.add(user.city)
            }
        }

        return listOfLocations
    }
}