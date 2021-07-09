package android.example.findmyfriends.ui.common

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@StateStrategyType(value= OneExecutionStateStrategy::class)
interface BaseView : MvpView {

    @AddToEndSingle
    fun makeToast(message: String)
}