package android.example.findmyfriends.ui.main

import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.ui.common.BaseActivity
import android.example.findmyfriends.ui.friends.FriendListActivity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.omegar.mvp.presenter.InjectPresenter
import com.omegar.mvp.presenter.ProvidePresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope


class MainActivity : BaseActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    var vkToken = "token"

    @ProvidePresenter
    fun providePresenter() = MainPresenter(vkToken)

    private lateinit var loginButton : Button
    private lateinit var infoButton : ImageButton

    private val alertDialogMessage = R.string.alert_dialog_message

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeElements()

        loginButton.setOnClickListener {
            presenter.login()
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
            makeToast(R.string.error)
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun startFriendsActivity(token: String) {
        val friendListIntent = Intent(this, FriendListActivity::class.java)
        friendListIntent.putExtra("vkToken", token)
        startActivity(friendListIntent)
    }

    override fun logInVk() {
        VK.login(this@MainActivity, listOf(VKScope.FRIENDS))
    }

    private fun initializeElements() {
        loginButton = findViewById(R.id.login_vk_button)
        infoButton = findViewById(R.id.app_info)
    }
}