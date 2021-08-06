package android.example.findmyfriends.ui.friends

import android.example.findmyfriends.FindMyFriendsApplication
import android.example.findmyfriends.R
import android.example.findmyfriends.data.repository.RepositoryImpl
import android.example.findmyfriends.data.source.local.model.UserInfo
import android.example.findmyfriends.data.source.remote.model.geo.UserLocationData
import android.example.findmyfriends.ui.common.BasePresenter
import android.util.SparseBooleanArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsPresenter(private val token: String) : BasePresenter<FriendsView>() {

    init {
        FindMyFriendsApplication.appComponent.inject(this)
    }

    @Inject
    lateinit var repositoryImpl: RepositoryImpl

    private var editTextText = ""
    private var adapterSelectedItemsState = false
    lateinit var allCheckBoxesArray: SparseBooleanArray

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showText(editTextText)
        viewState.setButtonState()
        viewState.setItemsFlagState()
    }

    override fun onDestroy() {
        super.onDestroy()

        repositoryImpl.clearData()
    }

    fun getUserList(): List<UserInfo> {

        val userList = repositoryImpl.downloadData(token)

        CoroutineScope(Dispatchers.Default).launch {
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
    ): List<UserLocationData> {

        var finalList = mutableListOf<UserLocationData>()

        val listOfChecked = getExclusiveIndices(list, inputList)
        if (listOfChecked.isNotEmpty()) {
            val checkedUsers = mutableListOf<UserInfo>()
            for (i in 0 until listOfChecked.size) checkedUsers.add(inputList[listOfChecked[i]])

            finalList = repositoryImpl.loadMapData(checkedUsers)
        }

        return finalList
    }

    private fun getExclusiveIndices(
        list: SparseBooleanArray,
        initialList: List<UserInfo>
    ): MutableList<Int> {

        val listOfChecked = mutableListOf<Int>()
        for (i in initialList.indices) {
            if (list.get(i)) listOfChecked.add(list.keyAt(i))
        }

        return listOfChecked
    }
}


