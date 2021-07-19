package android.example.findmyfriends.view.friendsactivity

import android.content.Context
import android.content.Intent
import android.example.findmyfriends.data.database.entity.UserInfo
import android.example.findmyfriends.model.remote.geodata.UserLocationData
import android.example.findmyfriends.repository.RepositoryImpl
import android.example.findmyfriends.view.common.BasePresenter
import android.example.findmyfriends.view.mapsactivity.MapsActivity
import android.util.SparseBooleanArray
import kotlinx.coroutines.*
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class FriendsPresenter @Inject constructor(context: Context, private val repositoryImpl : RepositoryImpl) : BasePresenter<FriendsView>(context) {

    var editTextText = ""
    var adapterSelectedItemsState = false
    lateinit var allCheckBoxesArray: SparseBooleanArray

    fun onViewAttach() {
        super.onFirstViewAttach()

        viewState.showText(editTextText)
        viewState.setButtonState()
        viewState.setItemsFlagState()
    }

    suspend fun getDataFromVk(token: String) : Boolean = repositoryImpl.downloadData(token)

    private suspend fun setUserList(): List<UserInfo> {
        return repositoryImpl.retrieveData()
    }

    suspend fun accessUserList(): List<UserInfo> = GlobalScope.async {
        return@async setUserList()
    }.await()

    fun clearData() = repositoryImpl.clearData()

    fun updateList(s: String, initialList: List<UserInfo>): MutableList<UserInfo> {
        val updated : MutableList<UserInfo> = mutableListOf()
        for(item in initialList) {
            if(item.name.lowercase().startsWith(s.lowercase())) {
                updated.add(item)
            }
        }
        return updated
    }

    fun openMapHandler() {
        if (isNetworkAvailable()) {
            viewState.makeToast("Проверьте подключение к интернету!")
        } else {
            viewState.startActivity()
        }
    }

    fun openMapHandler(list: SparseBooleanArray, inputList: List<UserInfo>) : ArrayList<UserLocationData> {

        var finalList = arrayListOf<UserLocationData>()

        val listOfChecked = getExclusiveIndices(list, inputList)
        if (listOfChecked.isNotEmpty()) {
            val checkedUsers = mutableListOf<UserInfo>()
            for (i in 0 until listOfChecked.size) checkedUsers.add(inputList[listOfChecked[i]])

            finalList = getCoordinatesByLocation(checkedUsers) as ArrayList<UserLocationData>
        }
        return finalList
    }

    private fun getExclusiveIndices(list: SparseBooleanArray, initialList: List<UserInfo>): MutableList<Int> {

        val listOfChecked = mutableListOf<Int>()
        for(i in initialList.indices) {
            try {
                if(list.valueAt(i)) listOfChecked.add(list.keyAt(i))
            } catch (e: ArrayIndexOutOfBoundsException) { }
        }

        return listOfChecked
    }

    private fun getCoordinatesByLocation(users: List<UserInfo>):
            MutableList<UserLocationData> = repositoryImpl.loadMapData(users)
}