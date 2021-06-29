package android.example.findmyfriends.presenters

import android.content.Context
import android.content.Intent
import android.example.findmyfriends.FriendListActivity
import android.example.findmyfriends.MainActivity
import android.example.findmyfriends.model.setVKToken
import android.example.findmyfriends.presenters.common.PresenterBase
import android.net.ConnectivityManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope

class PresenterMain(private val context: Context) : PresenterBase(context) {

    fun startNewActivityFromMain(viewActivity: MainActivity, token : String) {
        if(isNetworkAvailable())
        {
            if(accessVKToken() != "token") {
                startNewActivity(viewActivity, token)
            }
            else VK.login(viewActivity, listOf(VKScope.FRIENDS))
        }
        else makeToast("Проверьте подключение к интернету")
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


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager : ConnectivityManager =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}