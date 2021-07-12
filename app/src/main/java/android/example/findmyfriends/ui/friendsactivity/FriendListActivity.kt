package android.example.findmyfriends.ui.friendsactivity

import android.content.Context
import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.application.FindMyFriendsApplication
import android.example.findmyfriends.model.local.userList
import android.example.findmyfriends.model.remote.database.dao.UserInfoDao
import android.example.findmyfriends.model.remote.database.database.AppDatabase
import android.example.findmyfriends.model.remote.vk.friendsinfo.GetVkFriendsData
import android.example.findmyfriends.model.remote.vk.retrofitservice.VkFriendsService
import android.example.findmyfriends.ui.mapsactivity.MapsActivity
import android.example.findmyfriends.viewmodel.friendspresenter.FriendsPresenter
import android.example.findmyfriends.viewmodel.friendspresenter.friendsadapter.VkFriendListAdapter
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject

class FriendListActivity: MvpAppCompatActivity(R.layout.activity_friend_list), FriendsView {

    private val presenter by moxyPresenter { FriendsPresenter() }

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var usersDao: UserInfoDao

    private lateinit var vkAdapter: VkFriendListAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var editText : EditText
    private lateinit var selectAllButton : Button
    private lateinit var openMapButton : FloatingActionButton
    private lateinit var progressBar: ProgressBar

    private lateinit var request: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        (application as FindMyFriendsApplication).findMyFriendsComponent.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.onViewAttach()

        initializeElements()
        setRequest()

        val vkFriendsService = retrofit.create(VkFriendsService::class.java)
        vkFriendsService.getFriendList(request).enqueue(object : Callback<GetVkFriendsData> {
            override fun onResponse(call: Call<GetVkFriendsData>, response: Response<GetVkFriendsData>) {
                presenter.onResponse(response, usersDao)
            }

            override fun onFailure(call: Call<GetVkFriendsData>, t: Throwable) {
                makeToast("Не удалось загрузить данные")
            }
        })

        buildRecyclerView()

        selectAllButton.setOnClickListener {
            presenter.selectAll(vkAdapter)
        }

        editText.doAfterTextChanged {
            val s = editText.text
            presenter.controlTextFlow(s, vkAdapter)
        }

        openMapButton.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )

            if (!presenter.isNetworkAvailable(applicationContext)) {
                makeToast("Проверьте подключение к интернету!")
            } else {
                presenter.openMapHandler(this@FriendListActivity, vkAdapter)
                val mapIntent = Intent(this@FriendListActivity, MapsActivity::class.java)
                startActivity(mapIntent)
            }

            progressBar.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    private fun initializeElements() {
        editText = findViewById(R.id.enter_user)
        selectAllButton = findViewById(R.id.pick_all)
        openMapButton = findViewById(R.id.open_map_button)
        progressBar = findViewById(R.id.progress_bar)
    }

    private fun setRequest() {
        request =
            presenter.accessRequestVK() + intent.getStringExtra(presenter.accessCurrentToken())
                .toString() + presenter.accessMiscURL()
    }

    private fun buildRecyclerView() {
        recyclerView = findViewById(R.id.list_view)
        runBlocking {
            presenter.getUsers(usersDao)
        }
        val list = presenter.accessUserList()
        vkAdapter = VkFriendListAdapter(list, list, openMapButton)
        recyclerView.layoutManager = LinearLayoutManager(this@FriendListActivity)
        recyclerView.adapter = vkAdapter
        vkAdapter.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun showText(message: String?) {
        editText.setText(presenter.getText())
    }

    override fun setButtonState() {
        presenter.setChecksBeforeDestruction(vkAdapter)
    }

    override fun setItemsFlagState() {
        presenter.setItemsStateBeforeDestruction(vkAdapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.setArrayOfChecked(vkAdapter)
        presenter.setItemsState(vkAdapter)
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun finishAndRemoveTask() {
        GlobalScope.launch {
            usersDao.deleteAllUser()
        }
        userList.clear()
        super.finishAndRemoveTask()
    }
}

