package android.example.findmyfriends.ui.friends

import android.example.findmyfriends.data.source.remote.model.geo.UserLocationData
import android.example.findmyfriends.ui.common.BaseView
import com.omegar.mvp.viewstate.strategy.StateStrategyType
import com.omegar.mvp.viewstate.strategy.StrategyType

interface FriendsView : BaseView {

    @StateStrategyType(StrategyType.ADD_TO_END_SINGLE)
    fun showText(message: String?)

    @StateStrategyType(StrategyType.ADD_TO_END_SINGLE)
    fun setButtonState()

    @StateStrategyType(StrategyType.ADD_TO_END_SINGLE)
    fun setItemsFlagState()

    @StateStrategyType(StrategyType.ADD_TO_END_SINGLE)
    fun startMapActivity(users: List<UserLocationData>)
}