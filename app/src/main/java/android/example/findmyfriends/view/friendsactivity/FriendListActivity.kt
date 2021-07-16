package android.example.findmyfriends.view.friendsactivity

import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.application.App
import android.example.findmyfriends.data.database.entity.UserInfo
import android.example.findmyfriends.model.remote.geodata.UserLocationData
import android.example.findmyfriends.view.mapsactivity.MapsActivity
import android.example.findmyfriends.view.friendsactivity.friendsadapter.VkFriendListAdapter
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
import kotlinx.coroutines.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.*
import javax.inject.Inject

class FriendListActivity : MvpAppCompatActivity(), FriendsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: FriendsPresenter

    @ProvidePresenter
    fun providePresenter(): FriendsPresenter = presenter

    private lateinit var vkAdapter: VkFriendListAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var editText : EditText
    private lateinit var selectAllButton : Button
    private lateinit var openMapButton : FloatingActionButton
    private lateinit var progressBar: ProgressBar

    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {

        App.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.onViewAttach()

        initializeElements()

        GlobalScope.launch {
            if(!presenter.getDataFromVk(token))
                makeToast("Не удалось загрузить данные")
        }

        buildRecyclerView()

        selectAllButton.setOnClickListener {
            vkAdapter.selectAll()
        }

        editText.doAfterTextChanged {
            val updated = presenter.updateList(editText.text.toString(), vkAdapter.getList())
            vkAdapter.filterList(updated)
        }

        openMapButton.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            if (!presenter.isNetworkAvailable()) {
                makeToast("Проверьте подключение к интернету!")
            } else {
                val arrayOfCities = presenter.openMapHandler(vkAdapter.getChecked(), vkAdapter.getList())
                val mapIntent = Intent(this@FriendListActivity, MapsActivity::class.java)
                mapIntent.putExtra("arrayOfCities", arrayOfCities)
                startActivity(mapIntent)
            }

            progressBar.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun setItemsFlagState() {
        setItemsStateBeforeDestruction()
    }

    override fun onDestroy() {
        super.onDestroy()
        setArrayOfChecked()
        setItemsState()
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun finishAndRemoveTask() {
        GlobalScope.launch {
            presenter.clearData()
        }
        super.finishAndRemoveTask()
    }

    private fun initializeElements() {
        editText = findViewById(R.id.enter_user)
        selectAllButton = findViewById(R.id.pick_all)
        openMapButton = findViewById(R.id.open_map_button)
        progressBar = findViewById(R.id.progress_bar)
        token = intent.getStringExtra("vk token").toString()
    }

    private fun buildRecyclerView() {

        var list = mutableListOf<UserInfo>()
        GlobalScope.launch {
            list = presenter.accessUserList() as MutableList<UserInfo>
        }
        recyclerView = findViewById(R.id.list_view)
        vkAdapter = VkFriendListAdapter(list, list, openMapButton)
        recyclerView.layoutManager = LinearLayoutManager(this@FriendListActivity)
        recyclerView.adapter = vkAdapter
        vkAdapter.notifyDataSetChanged()
    }

    private fun setChecksBeforeDestruction() {
        try {
            vkAdapter.setChecked(presenter.allCheckBoxesArray)
            vkAdapter.notifyDataSetChanged()
        } catch (e: Exception) { }
    }

    private fun setItemsState() {
        presenter.adapterSelectedItemsState = vkAdapter.getItemsState()
    }

    private fun setArrayOfChecked() {
        presenter.allCheckBoxesArray = vkAdapter.getChecked()
    }

    private fun setItemsStateBeforeDestruction() {
        vkAdapter.setItemState( presenter.adapterSelectedItemsState)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun showText(message: String?) {
        editText.setText(presenter.editTextText)
    }

    override fun setButtonState() {
        setChecksBeforeDestruction()
    }
}