package android.example.findmyfriends.viewmodel.friendspresenter

import android.content.Context
import android.content.Intent
import android.example.findmyfriends.model.local.isDbCreated
import android.example.findmyfriends.model.local.locData
import android.example.findmyfriends.model.local.textViewText
import android.example.findmyfriends.model.remote.database.dao.UserInfoDao
import android.example.findmyfriends.model.remote.database.entity.UserInfo
import android.example.findmyfriends.model.remote.geodata.UserLocationData
import android.example.findmyfriends.model.remote.vk.friendsinfo.GetVkFriendsData
import android.example.findmyfriends.model.remote.vk.retrofitservice.VkFriendsService
import android.example.findmyfriends.ui.friendsactivity.FriendListActivity
import android.example.findmyfriends.ui.mapsactivity.MapsActivity
import android.example.findmyfriends.viewmodel.common.BasePresenter
import android.example.findmyfriends.viewmodel.friendspresenter.friendsadapter.VkFriendListAdapter
import android.example.findmyfriends.viewmodel.friendspresenter.moxyinterfaces.FriendsActivityView
import android.location.Geocoder
import android.text.Editable
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*


//использовать onFirstViewAttach() для загрузки списка друзей при певом запуске экрана
//использовать moxy для кнопки в Main Activity и TextView и мб кнопки в FriendsListActivity


@InjectViewState
class FriendsPresenter(context: Context) : MvpPresenter<FriendsActivityView>()  {

    init {
        viewState.showText(textViewText)
    }

    val basePresenter = BasePresenter(context)

    fun buildRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(basePresenter.accessBaseUrl())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun getResponse(vkFriendsService: VkFriendsService,
                            request: String, adapter: VkFriendListAdapter, db: UserInfoDao?) {
        vkFriendsService.getFriendList(request).enqueue(object : Callback<GetVkFriendsData> {
            override fun onResponse(call: Call<GetVkFriendsData>, response: Response<GetVkFriendsData>) {

                val result = response.body()?.response!!

                for(i in 0 until result.count!!) {
                    if(result.items?.get(i)?.city?.title != null) {
                        val nameOfUser = result.items[i].first_name + " " + result.items[i].last_name
                        val user = UserInfo(id = result.items[i].id!!, name = nameOfUser,
                            city = result.items[i].city?.title!!, photo_100 = result.items[i].photo_100!!)

                        GlobalScope.launch {
                            db?.insertUserInfo(user)
                        }
                    }
                }

                isDbCreated = true
                adapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<GetVkFriendsData>, t: Throwable) {
                basePresenter.makeToast("Не удалось загрузить данные")
            }
        })
    }

    private fun getOfflineFromDB(adapter: VkFriendListAdapter) {
        adapter.notifyDataSetChanged()
    }

    fun setOnlineOrOffline(db: UserInfoDao?, vkFriendsService: VkFriendsService, request: String, adapter: VkFriendListAdapter) {
        if(basePresenter.isNetworkAvailable() && !isDbCreated){
            getResponse(vkFriendsService, request, adapter, db)
        } else {
            getOfflineFromDB(adapter)
        }
    }

    fun getUsers(users: UserInfoDao?) : List<UserInfo> {
        val userList = mutableListOf<UserInfo>()
        try {
            GlobalScope.launch {
                userList.clear()
                userList.addAll(users?.getAllUsers()!!)
            }
        } catch (e: Exception) { }

        return userList
    }

    fun getText() = textViewText

    fun controlTextFlow(s: Editable?, adapter: VkFriendListAdapter) {
        val initialList = adapter.getList()
        val updated : MutableList<UserInfo> = mutableListOf()
        for(item in initialList) {
            if(item.name.lowercase(Locale.getDefault()).contains(s.toString().lowercase(Locale.getDefault())))
                updated.add(item)
        }
        adapter.filterList(updated)
    }

    fun openMapHandler(viewActivity: FriendListActivity, adapter: VkFriendListAdapter) {

        if(!basePresenter.isNetworkAvailable()) {
            basePresenter.makeToast("Проверьте подключение к интернету!")
        } else {
            val list = adapter.getChecked()
            val initialList: List<UserInfo> = adapter.getList()
            val listOfChecked = mutableListOf<Int>()

            for(i in initialList.indices) {
                try {
                    if(list.valueAt(i)) listOfChecked.add(list.keyAt(i))
                } catch (e: ArrayIndexOutOfBoundsException) { }
            }

            if(listOfChecked.isEmpty())
                basePresenter.makeToast("Вы ничего не выбрали!")
            else {
                val checkedUsers = mutableListOf<UserInfo>()
                for(i in 0 until listOfChecked.size) checkedUsers.add(initialList[listOfChecked[i]])

                locData = getCoordinatesByLocation(viewActivity, checkedUsers)

                val mapIntent = Intent(viewActivity, MapsActivity::class.java)
                viewActivity.startActivity(mapIntent)
            }
        }
    }

    private fun getCoordinatesByLocation(context: Context, users: List<UserInfo>): MutableList<UserLocationData> {

        val geocoder = Geocoder(context, Locale.getDefault())
        val listOfLocations = mutableListOf<UserLocationData>()

        val repetitiveCities = mutableListOf<String>()

        for (user in users) {
            if (!repetitiveCities.contains(user.city)) {
                val result = geocoder.getFromLocationName(user.city, 1)
                listOfLocations.add(
                    UserLocationData(
                        result[0].latitude,
                        result[0].longitude,
                        user.name,
                        user.city
                    )
                )
                repetitiveCities.add(user.city)
            }
        }

        return listOfLocations
    }

    fun accessRequestVK() = basePresenter.accessRequestVK()
    fun accessCurrentToken() = basePresenter.accessCurrentToken()
    fun accessMiscURL() = basePresenter.accessMiscURL()

}