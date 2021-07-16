package android.example.findmyfriends.view.mainactivity

import android.content.Context
import android.example.findmyfriends.application.App
import android.example.findmyfriends.view.common.BasePresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor (context: Context) : BasePresenter<MainView>(context) {

    fun onViewAttach() {
        super.onFirstViewAttach()
    }

    fun login(token: String) : Boolean {

        if(verifyVkToken(token))
            return true
        else if (isNetworkAvailable()) {
            VK.login(viewState as MainActivity, listOf(VKScope.FRIENDS))
            return true
        }
        else
            return false
    }

    private fun verifyVkToken(token: String) : Boolean = token != "token"
}