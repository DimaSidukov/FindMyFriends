package android.example.findmyfriends.ui.friendsactivity

import android.example.findmyfriends.R
import android.example.findmyfriends.model.remote.database.database.AppDatabase
import android.example.findmyfriends.model.remote.database.dao.UserInfoDao
import android.example.findmyfriends.viewmodel.friendspresenter.FriendsPresenter
import android.example.findmyfriends.model.remote.vk.retrofitservice.VkFriendsService
import android.example.findmyfriends.viewmodel.friendspresenter.friendsadapter.VkFriendListAdapter
import android.example.findmyfriends.viewmodel.friendspresenter.moxyinterfaces.FriendsActivityView
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Retrofit


//если стереть текст из эдитвью чекбокс сохраняется относительно позиции, а не пользователя

class FriendListActivity : AppCompatActivity(), FriendsActivityView {

    @InjectPresenter
    lateinit var presenter : FriendsPresenter

    private var db : AppDatabase? = null
    private var usersDao: UserInfoDao? = null

    private val editText: EditText = findViewById(R.id.enter_user)
    private val selectAllButton: Button = findViewById(R.id.pick_all)
    private val openMapButton: FloatingActionButton = findViewById(R.id.open_map_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = FriendsPresenter(this)

        val intentToken: String = intent.getStringExtra(presenter.accessCurrentToken()).toString()
        val request = presenter.accessRequestVK() + intentToken + presenter.accessMiscURL()

        db = AppDatabase.getAppDataBase(this)
        usersDao = db?.userInfoDao()

        val recyclerView: RecyclerView = findViewById(R.id.list_view)
        val vkAdapter = VkFriendListAdapter(presenter.getUsers(usersDao))
        recyclerView.layoutManager = LinearLayoutManager(this@FriendListActivity)
        recyclerView.adapter = vkAdapter

        val retrofit: Retrofit = presenter.buildRetrofit()
        val vkFriendsService: VkFriendsService = retrofit.create(VkFriendsService::class.java)

        presenter.setOnlineOrOffline(usersDao, vkFriendsService, request, vkAdapter)

        selectAllButton.setOnClickListener {
            vkAdapter.selectAll()
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                presenter.controlTextFlow(s, vkAdapter)
            }
        })

        openMapButton.setOnClickListener {
            presenter.openMapHandler(this, vkAdapter)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun showText(message: String?) {
        editText.setText(presenter.getText())
    }
}

