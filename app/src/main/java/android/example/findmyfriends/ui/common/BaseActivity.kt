package android.example.findmyfriends.ui.common

import android.widget.Toast
import com.omegar.mvp.MvpAppCompatActivity

open class BaseActivity : MvpAppCompatActivity(), BaseView {

    override fun makeToast(message: Int) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}