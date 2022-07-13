package com.cianjansen.warofsuits.ui.victory

import com.cianjansen.warofsuits.ui.base.BasePresenter
import com.cianjansen.warofsuits.ui.base.BaseView

class VictoryContract {
    interface Presenter : BasePresenter {
        fun setWinner(yourScore: Int)
    }

    interface View : BaseView<Presenter> {
        fun showResult(yourScore: Int, opponentScore: Int, result: VictoryActivity.GameResult)
    }
}
