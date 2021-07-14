package android.example.findmyfriends.viewmodel.mainpresenter

import android.content.Context
import android.example.findmyfriends.model.local.plain.setToken
import android.example.findmyfriends.ui.mainactivity.MainView
import android.example.findmyfriends.viewmodel.common.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor (context: Context) : BasePresenter<MainView>(context) {

    fun verifyVkToken(token: String) : Boolean = accessVKToken() != token

    fun setVkToken(token: String) = setToken(token)
}