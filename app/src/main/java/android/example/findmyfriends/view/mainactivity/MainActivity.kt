package android.example.findmyfriends.view.mainactivity

import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.application.App
import android.example.findmyfriends.view.friendsactivity.FriendListActivity
import android.os.Bundle
import android.widget.Button
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity() , BaseActivity {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenter

    private var token = "token"

    override fun onCreate(savedInstanceState: Bundle?) {

        App.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.login_vk_button)

        loginButton.setOnClickListener {
            if (presenter.login(token))
                startNewActivity()
            else {
                makeToast("Проверьте подключение к интернету")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val callback = object : VKAuthCallback {
                override fun onLogin(token: VKAccessToken) {
                    this@MainActivity.token = token.accessToken
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

//    override fun makeToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }

    private fun startNewActivity() {
        val friendListIntent = Intent(this, FriendListActivity::class.java)
        friendListIntent.putExtra("vk token", token)
        startActivity(friendListIntent)
    }
}