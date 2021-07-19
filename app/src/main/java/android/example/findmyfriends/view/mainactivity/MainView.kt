package android.example.findmyfriends.view.mainactivity

import android.example.findmyfriends.view.common.BaseView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@StateStrategyType(value= OneExecutionStateStrategy::class)
interface MainView : BaseView {

    @AddToEndSingle
    fun setToken(input: String)

    @AddToEndSingle
    fun startActivity()

    @AddToEndSingle
    fun logInVk()

}