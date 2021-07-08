package android.example.findmyfriends.viewmodel.friendspresenter.moxyinterfaces

import android.content.Context
import android.example.findmyfriends.ui.common.BaseView
import android.example.findmyfriends.ui.friendsactivity.FriendsView
import android.example.findmyfriends.viewmodel.friendspresenter.friendsadapter.VkFriendListAdapter
import android.widget.Toast
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@StateStrategyType(value= OneExecutionStateStrategy::class)
interface FriendsActivityView : MvpView, FriendsView {

    @AddToEndSingle
    fun showText(message: String?)

    @AddToEndSingle
    fun setButtonState()
}