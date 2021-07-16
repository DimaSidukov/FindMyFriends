package android.example.findmyfriends.view.mapsactivity

import android.example.findmyfriends.view.common.BaseView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value= OneExecutionStateStrategy::class)
interface MapView : BaseView {
}