package android.example.findmyfriends.ui.friends

import android.content.Intent
import android.example.findmyfriends.R
import android.example.findmyfriends.ui.common.BaseActivity
import android.example.findmyfriends.ui.friends.adapter.VkFriendListAdapter
import android.example.findmyfriends.ui.map.MapsActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omega_r.click.setClickListener
import com.omega_r.libs.extensions.list.toArrayList
import com.omegar.mvp.presenter.InjectPresenter
import com.omegar.mvp.presenter.ProvidePresenter
import kotlinx.coroutines.*
import java.util.*

class FriendListActivity : BaseActivity(), FriendsView {

    @InjectPresenter
    lateinit var presenter: FriendsPresenter

    @ProvidePresenter
    fun providePresenter() = FriendsPresenter(intent.getStringExtra("vkToken").toString())

    private lateinit var vkAdapter: VkFriendListAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var editText : EditText
    private lateinit var selectAllButton : Button
    private lateinit var openMapButton : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        initializeElements()
        buildRecyclerView()

        selectAllButton.setClickListener {
            vkAdapter.selectAll()
        }

        editText.doAfterTextChanged {
            val updated = presenter.updateList(editText.text.toString(), vkAdapter.getList())
            vkAdapter.filterList(updated)
        }

        openMapButton.setClickListener {
            presenter.openMapHandler()
        }
    }

    override fun startMapActivity(arrayOfCities: List<>) {

        val arrayOfCities  = presenter.getListOfUsersWithCities(vkAdapter.getChecked(), vkAdapter.getList()).toArrayList()

        val mapIntent = Intent(this@FriendListActivity, MapsActivity::class.java)
        mapIntent.putParcelableArrayListExtra("arrayOfCities", arrayOfCities)

        startActivity(mapIntent)
    }

    override fun setItemsFlagState() {
        setItemsStateBeforeDestruction()
    }

    override fun onDestroy() {
        super.onDestroy()
        setArrayOfChecked()
        setItemsState()
    }

    private fun initializeElements() {
        editText = findViewById(R.id.enter_user)
        selectAllButton = findViewById(R.id.pick_all)
        openMapButton = findViewById(R.id.open_map_button)
    }

    private fun buildRecyclerView() {
        vkAdapter = VkFriendListAdapter(presenter.getUserList(), openMapButton)
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
        vkAdapter.setItemState(presenter.adapterSelectedItemsState)
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