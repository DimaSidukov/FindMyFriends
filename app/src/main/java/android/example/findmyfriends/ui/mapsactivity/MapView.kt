package android.example.findmyfriends.ui.mapsactivity

import android.example.findmyfriends.ui.common.BaseView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value= OneExecutionStateStrategy::class)
interface MapView : BaseView {
}