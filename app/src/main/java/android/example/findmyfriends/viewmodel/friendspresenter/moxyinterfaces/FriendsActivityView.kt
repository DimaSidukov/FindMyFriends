package android.example.findmyfriends.viewmodel.friendspresenter.moxyinterfaces

import com.arellomobile.mvp.MvpView

interface FriendsActivityView : MvpView {

    fun showText(message: String?)
}