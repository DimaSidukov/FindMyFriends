package android.example.findmyfriends.ui.friendsactivity

import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.application.App
import android.example.findmyfriends.model.local.plain.allItemsSelectedState
import android.example.findmyfriends.model.local.plain.array
import android.example.findmyfriends.ui.mapsactivity.MapsActivity
import android.example.findmyfriends.viewmodel.friendspresenter.FriendsPresenter
import android.example.findmyfriends.viewmodel.friendspresenter.friendsadapter.VkFriendListAdapter
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
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.*
import javax.inject.Inject

//strictly restrain data from presenter, so presenter has to get all the data from database
//or local data store

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

        if(!presenter.getDataFromVk(token))
            makeToast("Не удалось загрузить данные")

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
                presenter.openMapHandler(vkAdapter.getChecked(), vkAdapter.getList())
                val mapIntent = Intent(this@FriendListActivity, MapsActivity::class.java)
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
        presenter.clearData()
        super.finishAndRemoveTask()
    }

    private fun initializeElements() {
        editText = findViewById(R.id.enter_user)
        selectAllButton = findViewById(R.id.pick_all)
        openMapButton = findViewById(R.id.open_map_button)
        progressBar = findViewById(R.id.progress_bar)
        token = intent.getStringExtra(presenter.accessCurrentToken()).toString()
    }

    private fun buildRecyclerView() {

        val list = presenter.accessUserList()
        recyclerView = findViewById(R.id.list_view)
        vkAdapter = VkFriendListAdapter(list, list, openMapButton)
        recyclerView.layoutManager = LinearLayoutManager(this@FriendListActivity)
        recyclerView.adapter = vkAdapter
        vkAdapter.notifyDataSetChanged()
    }

    private fun setChecksBeforeDestruction() {
        try {
            vkAdapter.setChecked(array)
            vkAdapter.notifyDataSetChanged()
        } catch (e: Exception) { }
    }

    private fun setItemsState() {
        allItemsSelectedState = vkAdapter.getItemsState()
    }

    private fun setArrayOfChecked() {
        array = vkAdapter.getChecked()
    }

    private fun setItemsStateBeforeDestruction() {
        vkAdapter.setItemState(allItemsSelectedState)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun showText(message: String?) {
        editText.setText(presenter.getText())
    }

    override fun setButtonState() {
        setChecksBeforeDestruction()
    }
}