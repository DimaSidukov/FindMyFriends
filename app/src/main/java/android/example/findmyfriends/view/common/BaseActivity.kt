package android.example.findmyfriends.view.common

import android.widget.Toast
import moxy.MvpAppCompatActivity

open class BaseActivity : MvpAppCompatActivity(), BaseView {

    override fun makeToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}