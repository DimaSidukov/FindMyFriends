package android.example.findmyfriends.viewmodel.friendspresenter.moxyinterfaces

import android.example.findmyfriends.viewmodel.friendspresenter.friendsadapter.VkFriendListAdapter
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@StateStrategyType(value= OneExecutionStateStrategy::class)
interface FriendsActivityView : MvpView {

    @AddToEndSingle
    fun showText(message: String?)

    @AddToEndSingle
    fun setButtonState()
}