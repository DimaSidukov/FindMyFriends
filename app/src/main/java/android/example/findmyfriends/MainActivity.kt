package android.example.findmyfriends

import android.content.Intent
import android.example.findmyfriends.presenters.PresenterMain
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

class MainActivity : AppCompatActivity() {

    private val presenter = PresenterMain(this)

    private val tokenTracker = object : VKTokenExpiredHandler {
        override fun onTokenExpired() {
            presenter.makeToast("Требуется повторная авторизация")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        VK.addTokenExpiredHandler(tokenTracker)

        val loginButton = findViewById<Button>(R.id.login_vk_button)

        loginButton.setOnClickListener {
            presenter.startNewActivityFromMain(this, presenter.accessCurrentToken())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = presenter.VKAuthorizationCallback(this)

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data , callback))
        {
            presenter.makeToast("Произошла ошибка!")
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}