package android.example.findmyfriends.ui.common

import com.omega_r.base.mvp.views.OmegaView
import com.omegar.mvp.MvpView
import com.omegar.mvp.viewstate.strategy.StateStrategyType
import com.omegar.mvp.viewstate.strategy.StrategyType

interface BaseView : MvpView {

    @StateStrategyType(StrategyType.ADD_TO_END_SINGLE)
    fun makeToast(message: Int)
}
