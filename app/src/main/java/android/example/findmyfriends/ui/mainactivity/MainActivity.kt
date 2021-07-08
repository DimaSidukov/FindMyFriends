package android.example.findmyfriends.ui.mainactivity

import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.application.FindMyFriendsApplication
import android.example.findmyfriends.ui.friendsactivity.FriendListActivity
import android.example.findmyfriends.viewmodel.mainpresenter.MainPresenter
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainView{

    @Inject
    lateinit var presenter : MainPresenter

    private val falseToken = "token"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as FindMyFriendsApplication).findMyFriendsComponent.inject(this)

        val loginButton = findViewById<Button>(R.id.login_vk_button)

        loginButton.setOnClickListener {
            if(presenter.isNetworkAvailable(this)) {
                if(presenter.verifyVkToken(falseToken)) {
                    startNewActivity()
                }
                else VK.login(this, listOf(VKScope.FRIENDS))
            }
            else {
                if(presenter.verifyVkToken(falseToken)) {
                    startNewActivity()
                }
                else makeToast("Проверьте подключение к интернету")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val callback = object : VKAuthCallback {
                override fun onLogin(token: VKAccessToken) {
                    presenter.setVkToken(token.accessToken)
                    startNewActivity()
                }

                override fun onLoginFailed(errorCode: Int) {
                    makeToast("Не удалось авторизоваться")
                }
            }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data , callback)) {
            makeToast("Произошла ошибка!")
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startNewActivity() {
        val friendListIntent = Intent(this, FriendListActivity::class.java)
        friendListIntent.putExtra(presenter.accessVKToken(), presenter.accessVKToken())
        startActivity(friendListIntent)
    }
}