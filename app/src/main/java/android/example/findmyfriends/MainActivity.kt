package android.example.findmyfriends

import android.content.Intent
import android.example.findmyfriends.mutabledata.vkAccessToken
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.login_vk_button)

        loginButton.setOnClickListener {
            if(isNetworkAvailable())
                VK.login(this, listOf(VKScope.FRIENDS))
            else Toast.makeText(this@MainActivity, "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Log.d("VK access token", token.accessToken)
                val friendListIntent = Intent(this@MainActivity, FriendListActivity::class.java)
                friendListIntent.putExtra(vkAccessToken, token.accessToken)
                startActivity(friendListIntent)
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(this@MainActivity, "Не удалось авторизоваться", Toast.LENGTH_SHORT).show()
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            Toast.makeText(this@MainActivity, "Произошла ошибка!", Toast.LENGTH_SHORT).show()
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}