package android.example.findmyfriends.ui.friends

import android.example.findmyfriends.FindMyFriendsApplication
import android.example.findmyfriends.R
import android.example.findmyfriends.data.repository.RepositoryImpl
import android.example.findmyfriends.data.source.local.model.UserInfo
import android.example.findmyfriends.data.source.remote.model.geo.UserLocationData
import android.example.findmyfriends.ui.common.BasePresenter
import android.util.SparseBooleanArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class FriendsPresenter @Inject constructor(private val token: String) : BasePresenter<FriendsView>() {

    init {
        FindMyFriendsApplication.appComponent.inject(this)
    }

    @Inject
    lateinit var repositoryImpl: RepositoryImpl

    var editTextText = ""
    var adapterSelectedItemsState = false
    lateinit var allCheckBoxesArray: SparseBooleanArray

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showText(editTextText)
        viewState.setButtonState()
        viewState.setItemsFlagState()
    }

    fun getUserList(): List<UserInfo> {

        val userList = repositoryImpl.downloadData(token)

        GlobalScope.launch(Dispatchers.Main) {
            repositoryImpl.loadAllDataToDataBase(userList)
        }
        return userList
    }

    fun updateList(s: String, initialList: List<UserInfo>): MutableList<UserInfo> {
        val updated: MutableList<UserInfo> = mutableListOf()
        for (item in initialList) {
            if (item.name.lowercase().startsWith(s.lowercase())) {
                updated.add(item)
            }
        }
        return updated
    }

    fun openMapHandler() {
        if (isNetworkAvailable()) {
            viewState.startMapActivity()
        } else {
            viewState.makeToast(R.string.check_internet_connection)
        }
    }

    fun getListOfUsersWithCities(
        list: SparseBooleanArray,
        inputList: List<UserInfo>
    ): ArrayList<UserLocationData> {

        var finalList = arrayListOf<UserLocationData>()

        val listOfChecked = getExclusiveIndices(list, inputList)
        if (listOfChecked.isNotEmpty()) {
            val checkedUsers = mutableListOf<UserInfo>()
            for (i in 0 until listOfChecked.size) checkedUsers.add(inputList[listOfChecked[i]])

            finalList = getCoordinatesByLocation(checkedUsers) as ArrayList<UserLocationData>
        }
        return finalList
    }

    private fun getExclusiveIndices(
        list: SparseBooleanArray,
        initialList: List<UserInfo>
    ): MutableList<Int> {

        val listOfChecked = mutableListOf<Int>()
        for (i in initialList.indices) {
            try {
                if (list.valueAt(i)) listOfChecked.add(list.keyAt(i))
            } catch (e: ArrayIndexOutOfBoundsException) {
            }
        }

        return listOfChecked
    }

    private fun getCoordinatesByLocation(users: List<UserInfo>) : MutableList<UserLocationData> {
        var list : MutableList<UserLocationData>
        runBlocking {
            list = repositoryImpl.loadMapData(users)
        }
        return list
    }
}