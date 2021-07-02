package android.example.findmyfriends.ui.mainactivity

import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.viewmodel.mainpresenter.MainPresenter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK


class MainActivity : AppCompatActivity() {

    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.login_vk_button)

        loginButton.setOnClickListener {
            presenter.startNewActivityFromMain(this, presenter.accessCurrentToken())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val callback = presenter.VKAuthorizationCallback(this)

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data , callback)) {
            presenter.makeToast("Произошла ошибка!")
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}