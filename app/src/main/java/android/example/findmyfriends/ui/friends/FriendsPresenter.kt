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

    var editTextText = ""
    var adapterSelectedItemsState = false
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

    fun openMapHandler(list: SparseBooleanArray, inputList: List<UserInfo>) {
        if (isNetworkAvailable()) {
            viewState.startMapActivity(getListOfUsersWithCities(list, inputList))
        } else {
            viewState.makeToast(R.string.check_internet_connection)
        }
    }

    private fun getListOfUsersWithCities(
        list: SparseBooleanArray,
        inputList: List<UserInfo>
    ): List<UserLocationData> {

        val innerList = mutableListOf<UserInfo>()

        for(index in 0 until list.size()) {
            innerList.add(inputList[list.keyAt(index)])
        }

        return repositoryImpl.loadMapData(innerList)
    }
}