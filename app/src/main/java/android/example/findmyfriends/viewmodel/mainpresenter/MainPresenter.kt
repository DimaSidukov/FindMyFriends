package android.example.findmyfriends.viewmodel.mainpresenter

import android.example.findmyfriends.model.local.setToken
import android.example.findmyfriends.viewmodel.common.BasePresenter

class MainPresenter() : BasePresenter {

    fun verifyVkToken(token: String) : Boolean = accessVKToken() != token

    fun setVkToken(token: String) = setToken(token)
}