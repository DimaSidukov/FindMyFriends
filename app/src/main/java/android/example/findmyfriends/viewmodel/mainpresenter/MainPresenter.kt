package android.example.findmyfriends.viewmodel.mainpresenter

import android.content.Context
import android.content.Intent
import android.example.findmyfriends.model.local.setVKToken
import android.example.findmyfriends.ui.friendsactivity.FriendListActivity
import android.example.findmyfriends.ui.mainactivity.MainActivity
import android.example.findmyfriends.viewmodel.common.BasePresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class MainPresenter(context: Context) : BasePresenter(context){

    fun startNewActivityFromMain(viewActivity: MainActivity, token : String) {
        if(isNetworkAvailable()) {
            if(accessVKToken() != "token") {
                startNewActivity(viewActivity, token)
            }
            else VK.login(viewActivity, listOf(VKScope.FRIENDS))
        }
        else {
            if(accessVKToken() != "token") {
                startNewActivity(viewActivity, token)
            }
            else makeToast("Проверьте подключение к интернету")
        }
    }

    fun VKAuthorizationCallback(viewActivity: MainActivity): VKAuthCallback {
        return object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                setVKToken(token.accessToken)
                startNewActivity(viewActivity, token.accessToken)
            }

            override fun onLoginFailed(errorCode: Int) {
                makeToast("Не удалось авторизоваться")
            }
        }
    }

    fun startNewActivity(viewActivity: MainActivity, token : String) {
        val friendListIntent = Intent(viewActivity, FriendListActivity::class.java)
        friendListIntent.putExtra(accessVKToken(), token)
        viewActivity.startActivity(friendListIntent)
    }
}