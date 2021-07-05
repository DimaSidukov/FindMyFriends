package android.example.findmyfriends.ui.friendsactivity

import android.example.findmyfriends.R
import android.example.findmyfriends.model.local.array
import android.example.findmyfriends.model.remote.database.dao.UserInfoDao
import android.example.findmyfriends.model.remote.database.database.AppDatabase
import android.example.findmyfriends.model.remote.vk.retrofitservice.VkFriendsService
import android.example.findmyfriends.viewmodel.friendspresenter.FriendsPresenter
import android.example.findmyfriends.viewmodel.friendspresenter.friendsadapter.VkFriendListAdapter
import android.example.findmyfriends.viewmodel.friendspresenter.moxyinterfaces.FriendsActivityView
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import retrofit2.Retrofit

class FriendListActivity : MvpAppCompatActivity(R.layout.activity_friend_list), FriendsActivityView {

    private val presenter by moxyPresenter { FriendsPresenter(applicationContext) }

    private var db : AppDatabase? = null
    private var usersDao: UserInfoDao? = null
    private lateinit var vkAdapter: VkFriendListAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var editText : EditText
    private lateinit var selectAllButton : Button
    private lateinit var openMapButton : FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.onViewAttach()

        declareElements()

        val intentToken: String = intent.getStringExtra(presenter.accessCurrentToken()).toString()
        val request = presenter.accessRequestVK() + intentToken + presenter.accessMiscURL()

        db = AppDatabase.getAppDataBase(this)
        usersDao = db?.userInfoDao()

        buildRecyclerView()

        val retrofit: Retrofit = presenter.buildRetrofit()
        val vkFriendsService: VkFriendsService = retrofit.create(VkFriendsService::class.java)

        presenter.setOnlineOrOffline(usersDao, vkFriendsService, request, vkAdapter)

        selectAllButton.setOnClickListener {
            presenter.selectAll(vkAdapter)
        }

        editText.doAfterTextChanged {
            val s = editText.text
            presenter.controlTextFlow(s, vkAdapter)
        }

        openMapButton.setOnClickListener {
            presenter.openMapHandler(this, vkAdapter)
        }
    }

    private fun declareElements() {
        editText = findViewById(R.id.enter_user)
        selectAllButton = findViewById(R.id.pick_all)
        openMapButton = findViewById(R.id.open_map_button)
    }

    private fun buildRecyclerView() {
        recyclerView = findViewById(R.id.list_view)
        vkAdapter = VkFriendListAdapter(presenter.getUsers(usersDao), presenter.getUsers(usersDao))
        recyclerView.layoutManager = LinearLayoutManager(this@FriendListActivity)
        recyclerView.adapter = vkAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun showText(message: String?) {
        editText.setText(presenter.getText())
    }

    override fun onDestroy() {
        super.onDestroy()
        array = vkAdapter.getChecked()
    }

    override fun setButtonState() {
        try {
            vkAdapter.setChecked(array)
            val (first, _) = presenter.getExclusiveIndices(vkAdapter)
            Log.d("USERS PICKED", first.toString())
            Log.d("USERS PICKED LENGTH", first.size.toString())
            vkAdapter.notifyDataSetChanged()
        } catch (e: Exception) { }
    }
}

