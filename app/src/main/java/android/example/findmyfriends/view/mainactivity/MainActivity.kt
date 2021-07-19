package android.example.findmyfriends.view.mainactivity

import android.app.Activity
import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.application.App
import android.example.findmyfriends.view.common.BaseActivity
import android.example.findmyfriends.view.friendsactivity.FriendListActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class MainActivity : BaseActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenter

    var vkToken = "token"

    override fun onCreate(savedInstanceState: Bundle?) {

        App.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.login_vk_button)

        loginButton.setOnClickListener {
            presenter.login(vkToken)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val callback = presenter.onCallbackResult()

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data , callback)) {
            makeToast("Произошла ошибка!")
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun startActivity() {
        val friendListIntent = Intent(this, FriendListActivity::class.java)
        friendListIntent.putExtra("vktoken", vkToken)
        startActivity(friendListIntent)
    }

    override fun logInVk() {
        VK.login(this@MainActivity, listOf(VKScope.FRIENDS))
    }

    override fun setToken(input: String) {
        vkToken = input
    }
}