package android.example.findmyfriends.ui.main

import android.example.findmyfriends.ui.common.BaseView
import com.omegar.mvp.viewstate.strategy.StateStrategyType
import com.omegar.mvp.viewstate.strategy.StrategyType

interface MainView : BaseView {

    @StateStrategyType(StrategyType.ADD_TO_END_SINGLE)
    fun startFriendsActivity(token: String)

    @StateStrategyType(StrategyType.ADD_TO_END_SINGLE)
    fun logInVk()

}