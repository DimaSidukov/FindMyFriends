package android.example.findmyfriends.view.mainactivity

import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.application.App
import android.example.findmyfriends.view.common.BaseActivity
import android.example.findmyfriends.view.friendsactivity.FriendListActivity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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

    private lateinit var loginButton : Button
    private lateinit var infoButton : ImageButton

    private val alertDialogMessage = R.string.alert_dialog_message

    override fun onCreate(savedInstanceState: Bundle?) {

        App.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeElements()

        loginButton.setOnClickListener {
            presenter.login(vkToken)
        }

        infoButton.setOnClickListener {

            val alertDialogBuilder = AlertDialog.Builder(this, R.style.AlertDialog)
            alertDialogBuilder.setMessage(alertDialogMessage).setCancelable(true)
            alertDialogBuilder.setPositiveButton("OK", null)

            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.show()

            val messageView = alertDialog.findViewById<TextView>(android.R.id.message)!!
            messageView.gravity = Gravity.CENTER
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
        friendListIntent.putExtra("vkToken", vkToken)
        startActivity(friendListIntent)
    }

    override fun logInVk() {
        VK.login(this@MainActivity, listOf(VKScope.FRIENDS))
    }

    override fun setToken(input: String) {
        vkToken = input
    }

    private fun initializeElements() {
        loginButton = findViewById(R.id.login_vk_button)
        infoButton = findViewById(R.id.app_info)
    }
}