package android.example.findmyfriends.view.friendsactivity

import android.example.findmyfriends.view.common.BaseView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@StateStrategyType(value= OneExecutionStateStrategy::class)
interface FriendsView : BaseView {

    @AddToEndSingle
    fun showText(message: String?)

    @AddToEndSingle
    fun setButtonState()

    @AddToEndSingle
    fun setItemsFlagState()

    fun startActivity()
}