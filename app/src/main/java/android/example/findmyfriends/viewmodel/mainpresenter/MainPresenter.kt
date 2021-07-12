package android.example.findmyfriends.viewmodel.mainpresenter

import android.example.findmyfriends.model.local.setToken
import android.example.findmyfriends.model.local.userList
import android.example.findmyfriends.model.remote.database.database.AppDatabase
import android.example.findmyfriends.ui.mainactivity.MainView
import android.example.findmyfriends.viewmodel.common.BasePresenter
import moxy.InjectViewState

@InjectViewState
class MainPresenter() : BasePresenter<MainView>() {

    fun verifyVkToken(token: String) : Boolean = accessVKToken() != token

    fun setVkToken(token: String) = setToken(token)

    fun clearDataBase() {
        AppDatabase.destroyDataBase()
    }
}