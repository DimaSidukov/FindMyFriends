package android.example.findmyfriends

import android.content.Intent
<<<<<<< HEAD
import android.example.findmyfriends.presenters.PresenterMain
=======
import android.example.findmyfriends.mutabledata.makeToast
import android.example.findmyfriends.mutabledata.vkAccessToken
import android.net.ConnectivityManager
>>>>>>> dcff0ea775896c1f3bb9ca03daa144c61f2c302a
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
<<<<<<< HEAD

class MainActivity : AppCompatActivity() {

    private val presenter = PresenterMain(this)

    private val tokenTracker = object : VKTokenExpiredHandler {
        override fun onTokenExpired() {
            presenter.makeToast("Требуется повторная авторизация")
        }
    }

=======
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope

class MainActivity : AppCompatActivity() {
>>>>>>> dcff0ea775896c1f3bb9ca03daa144c61f2c302a
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        VK.addTokenExpiredHandler(tokenTracker)

        val loginButton = findViewById<Button>(R.id.login_vk_button)

        loginButton.setOnClickListener {
<<<<<<< HEAD
            presenter.startNewActivityFromMain(this, presenter.accessCurrentToken())
=======
            if(isNetworkAvailable())
            {
                if(vkAccessToken != "token") {
                    val friendListIntent = Intent(this@MainActivity, FriendListActivity::class.java)
                    friendListIntent.putExtra(vkAccessToken, vkAccessToken)
                    startActivity(friendListIntent)
                }
                else VK.login(this, listOf(VKScope.FRIENDS))
            }
            else makeToast(this, "Проверьте подключение к интернету")
>>>>>>> dcff0ea775896c1f3bb9ca03daa144c61f2c302a
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
<<<<<<< HEAD
        val callback = presenter.VKAuthorizationCallback(this)

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data , callback))
        {
            presenter.makeToast("Произошла ошибка!")
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
=======
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                val friendListIntent = Intent(this@MainActivity, FriendListActivity::class.java)
                vkAccessToken = token.accessToken
                friendListIntent.putExtra(vkAccessToken, token.accessToken)
                startActivity(friendListIntent)
            }

            override fun onLoginFailed(errorCode: Int) {
                makeToast(this@MainActivity,"Не удалось авторизоваться")
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            makeToast(this@MainActivity, "Произошла ошибка!")
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private val tokenTracker = object : VKTokenExpiredHandler {
        override fun onTokenExpired() {
            makeToast(this@MainActivity, "Требуется повторная авторизация")
        }

    }
>>>>>>> dcff0ea775896c1f3bb9ca03daa144c61f2c302a
}