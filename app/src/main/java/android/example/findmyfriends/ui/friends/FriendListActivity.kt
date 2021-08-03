package android.example.findmyfriends.ui.friends

import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.FindMyFriendsApplication
import android.example.findmyfriends.ui.common.BaseActivity
import android.example.findmyfriends.ui.friends.adapter.VkFriendListAdapter
import android.example.findmyfriends.ui.map.MapsActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omegar.mvp.presenter.InjectPresenter
import com.omegar.mvp.presenter.PresenterType
import com.omegar.mvp.presenter.ProvidePresenter
import kotlinx.coroutines.*
import java.util.*

class FriendListActivity : BaseActivity(), FriendsView {

    @InjectPresenter
    lateinit var presenter: FriendsPresenter

    @ProvidePresenter
    fun providePresenter() : FriendsPresenter = presenter

    private lateinit var vkAdapter: VkFriendListAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var editText : EditText
    private lateinit var selectAllButton : Button
    private lateinit var openMapButton : FloatingActionButton
    private lateinit var progressBar: ProgressBar

    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {

        FindMyFriendsApplication.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        initializeElements()
        buildRecyclerView()

        selectAllButton.setOnClickListener {
            vkAdapter.selectAll()
        }

        editText.doAfterTextChanged {
            val updated = presenter.updateList(editText.text.toString(), vkAdapter.getList())
            vkAdapter.filterList(updated)
        }

        openMapButton.setOnClickListener {
            presenter.openMapHandler()
        }
    }

    override fun startMapActivity() {

        progressBar.visibility = View.VISIBLE
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        Log.d("progress bar visibility", progressBar.visibility.toString())

        val arrayOfCities = presenter.getListOfUsersWithCities(vkAdapter.getChecked(), vkAdapter.getList())

        val mapIntent = Intent(this@FriendListActivity, MapsActivity::class.java)
        mapIntent.putParcelableArrayListExtra("arrayOfCities", arrayOfCities)
        startActivity(mapIntent)

        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        progressBar.visibility = View.INVISIBLE
        Log.d("progress bar visibility", progressBar.visibility.toString())
    }

    override fun setItemsFlagState() {
        setItemsStateBeforeDestruction()
    }

    override fun onDestroy() {
        super.onDestroy()
        setArrayOfChecked()
        setItemsState()
    }

    //fix this
    override fun finishAndRemoveTask() {
        presenter.deleteDataBase()
        super.finishAndRemoveTask()
    }

    private fun initializeElements() {
        editText = findViewById(R.id.enter_user)
        selectAllButton = findViewById(R.id.pick_all)
        openMapButton = findViewById(R.id.open_map_button)
        progressBar = findViewById(R.id.progress_bar)
        token = intent.getStringExtra("vkToken").toString()
    }

    private fun buildRecyclerView() {
        vkAdapter = VkFriendListAdapter(presenter.getUserList(token), openMapButton)
        recyclerView = findViewById(R.id.list_view)
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