package android.example.findmyfriends.view.mainactivity

import android.content.Context
import android.example.findmyfriends.view.common.BasePresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor (context: Context) : BasePresenter<MainView>(context) {

    fun onViewAttach() {
        super.onFirstViewAttach()
    }

    fun login(token: String) {

        if(isNetworkAvailable()) {
            if(verifyVkToken(token)) {
                viewState.startActivity()
            }
            else {
                viewState.logInVk()
            }
        }
        else {
            if(verifyVkToken(token)) {
                viewState.startActivity()
            }
            else viewState.makeToast("Проверьте подключение к интернету")
        }
    }

    fun onCallbackResult() : VKAuthCallback {

        return object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                viewState.setToken(token.accessToken)
                viewState.startActivity()
            }

            override fun onLoginFailed(errorCode: Int) {
                viewState.makeToast("Не удалось авторизоваться")
            }
        }
    }

    private fun verifyVkToken(token: String) : Boolean = token != "token"
}