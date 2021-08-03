package android.example.findmyfriends.ui.main

import android.content.Context
import android.example.findmyfriends.ui.common.BasePresenter
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import javax.inject.Inject

//раньше можно было вставлять аннотацию @InjectViewState, а сейчас уже нельзя
class MainPresenter @Inject constructor(context: Context) : BasePresenter<MainView>(context) {

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
            } else viewState.makeToast("Проверьте подключение к интернету")
        }
    }

    fun onCallbackResult() : VKAuthCallback {

        return object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                viewState.setToken(token.accessToken)
                viewState.startFriendsActivity()
            }

            override fun onLoginFailed(errorCode: Int) {
                viewState.makeToast("Не удалось авторизоваться")
            }
        }
    }

    private fun verifyVkToken(token: String) : Boolean = token != "token"
}