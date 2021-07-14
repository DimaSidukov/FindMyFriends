package android.example.findmyfriends.viewmodel.friendspresenter

import android.content.Context
import android.example.findmyfriends.model.local.locData
import android.example.findmyfriends.model.local.textViewText
import android.example.findmyfriends.model.local.userList
import android.example.findmyfriends.model.remote.database.entity.UserInfo
import android.example.findmyfriends.model.remote.geodata.UserLocationData
import android.example.findmyfriends.repository.database.DataBaseInterfaceHandler
import android.example.findmyfriends.repository.geocoder.GeocoderInterfaceHandler
import android.example.findmyfriends.repository.networkapi.RetrofitInterfaceHandler
import android.example.findmyfriends.ui.friendsactivity.FriendsView
import android.example.findmyfriends.viewmodel.common.BasePresenter
import android.util.SparseBooleanArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class FriendsPresenter @Inject constructor(context: Context,
                                           val dbHandler : DataBaseInterfaceHandler,
                                           val retrofitHandler: RetrofitInterfaceHandler,
                                           val geocoder: GeocoderInterfaceHandler) : BasePresenter<FriendsView>(context) {

    fun onViewAttach() {
        super.onFirstViewAttach()

        viewState.showText(textViewText)
        viewState.setButtonState()
        viewState.setItemsFlagState()
    }

    fun getDataFromVk(token: String) : Boolean {
        return retrofitHandler.runRetrofit(token)
    }

    private fun setUserList() {
        GlobalScope.launch(Dispatchers.IO) {
            userList = dbHandler.getFromDataBase()
        }
    }

    fun accessUserList(): List<UserInfo> {
        setUserList()
        return userList
    }

    fun clearData() {
        GlobalScope.launch {
            dbHandler.deleteDataBase()
        }
        userList = listOf()
    }

    fun updateList(s: String, initialList: List<UserInfo>): MutableList<UserInfo> {
        val updated : MutableList<UserInfo> = mutableListOf()
        for(item in initialList) {
            if(item.name.lowercase().startsWith(s.lowercase())) {
                updated.add(item)
            }
        }
        return updated
    }

    fun getText() = textViewText

    fun openMapHandler(list: SparseBooleanArray, inputList: List<UserInfo>) {

        GlobalScope.launch {
            val (listOfChecked, initialList) = getExclusiveIndices(list, inputList)
            if (listOfChecked.isNotEmpty()) {
                val checkedUsers = mutableListOf<UserInfo>()
                for (i in 0 until listOfChecked.size) checkedUsers.add(initialList[listOfChecked[i]])

                locData = getCoordinatesByLocation(checkedUsers)
            }
        }
    }

    fun getExclusiveIndices(list: SparseBooleanArray, initialList: List<UserInfo>): Pair<MutableList<Int>, List<UserInfo>> {

        val listOfChecked = mutableListOf<Int>()
        for(i in initialList.indices) {
            try {
                if(list.valueAt(i)) listOfChecked.add(list.keyAt(i))
            } catch (e: ArrayIndexOutOfBoundsException) { }
        }

        return Pair(listOfChecked, initialList)
    }

    private fun getCoordinatesByLocation(users: List<UserInfo>):
            MutableList<UserLocationData> = geocoder.buildList(users)
}