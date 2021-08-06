package android.example.findmyfriends.ui.main

import android.example.findmyfriends.FindMyFriendsApplication
import android.example.findmyfriends.R
import android.example.findmyfriends.ui.common.BasePresenter
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback

class MainPresenter(private val vkToken:String) : BasePresenter<MainView>() {

    init {
        FindMyFriendsApplication.appComponent.inject(this)
    }

    fun login(token: String) {

        if(isNetworkAvailable()) {
            if(verifyVkToken(token)) {
                viewState.startFriendsActivity()
            } else {
                viewState.logInVk()
            }
        }
        else {
            if(verifyVkToken(token)) {
                viewState.startFriendsActivity()
            } else viewState.makeToast(R.string.check_internet_connection)
        }
    }

    fun onCallbackResult() : VKAuthCallback {

        return object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                viewState.setToken(token.accessToken)
                viewState.startFriendsActivity()
            }

            override fun onLoginFailed(errorCode: Int) {
                viewState.makeToast(R.string.failed_to_authorize)
            }
        }
    }

    private fun verifyVkToken(token: String) : Boolean = token != "token"
}