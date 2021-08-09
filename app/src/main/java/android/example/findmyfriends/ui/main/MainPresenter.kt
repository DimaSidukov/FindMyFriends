package android.example.findmyfriends.ui.main

import android.example.findmyfriends.FindMyFriendsApplication
import android.example.findmyfriends.R
import android.example.findmyfriends.ui.common.BasePresenter
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback

class MainPresenter(private var vkToken: String) : BasePresenter<MainView>() {

    init {
        FindMyFriendsApplication.appComponent.inject(this)
    }

    private var currentToken = vkToken

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        vkToken = currentToken
    }

    fun login() {

        if(isNetworkAvailable()) {
            if(verifyVkToken()) {
                viewState.startFriendsActivity(vkToken)
            } else {
                viewState.logInVk()
            }
        }
        else {
            if(verifyVkToken()) {
                viewState.startFriendsActivity(vkToken)
            } else viewState.makeToast(R.string.check_internet_connection)
        }
    }

    fun onCallbackResult() : VKAuthCallback {

        return object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                vkToken = token.accessToken
                //viewState.setToken(token.accessToken)
                viewState.startFriendsActivity(vkToken)
            }

            override fun onLoginFailed(errorCode: Int) {
                viewState.makeToast(R.string.failed_to_authorize)
            }
        }
    }

    private fun verifyVkToken() : Boolean = vkToken != "token"
}