package android.example.findmyfriends.ui.friendsactivity

import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.application.FindMyFriendsApplication
import android.example.findmyfriends.model.remote.database.dao.UserInfoDao
import android.example.findmyfriends.model.remote.vk.retrofitservice.VkFriendsService
import android.example.findmyfriends.ui.mapsactivity.MapsActivity
import android.example.findmyfriends.viewmodel.friendspresenter.FriendsPresenter
import android.example.findmyfriends.viewmodel.friendspresenter.friendsadapter.VkFriendListAdapter
import android.example.findmyfriends.viewmodel.friendspresenter.moxyinterfaces.FriendsActivityView
import android.os.Bundle
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
import kotlinx.coroutines.runBlocking
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import retrofit2.Retrofit
import javax.inject.Inject

class FriendListActivity @Inject constructor() : MvpAppCompatActivity(R.layout.activity_friend_list), FriendsActivityView {

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
        buildRecyclerView()

        val vkFriendsService: VkFriendsService = retrofit.create(VkFriendsService::class.java)
        if (!presenter.onResponse(usersDao, vkFriendsService, request, vkAdapter))
            makeToast("Не удалось загрузить данные")

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
            runBlocking {

                if (!presenter.isNetworkAvailable(applicationContext)) {
                    makeToast("Проверьте подключение к интернету!")
                } else {
                    presenter.openMapHandler(this@FriendListActivity, vkAdapter)
                    val mapIntent = Intent(this@FriendListActivity, MapsActivity::class.java)
                    startActivity(mapIntent)
                }
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
        vkAdapter = VkFriendListAdapter(presenter.getUsers(usersDao), presenter.getUsers(usersDao), openMapButton)
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
        presenter.setArrayOfChecked(vkAdapter)
    }

    override fun setButtonState() {
        presenter.setChecksBeforeDestruction(vkAdapter)
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

