package android.example.findmyfriends.ui.common

import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@StateStrategyType(value= OneExecutionStateStrategy::class)
interface BaseView {

    @AddToEndSingle
    fun makeToast(message: String)
}