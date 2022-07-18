package com.cianjansen.warofsuits.ui.activity.victory

import com.cianjansen.warofsuits.ui.activity.base.BasePresenter
import com.cianjansen.warofsuits.ui.activity.base.BaseView

interface VictoryContract {
    interface Presenter : BasePresenter {
        fun setWinner(yourScore: Int)
    }

    interface View : BaseView<Presenter> {
        fun showResult(yourScore: Int, opponentScore: Int, result: VictoryActivity.GameResult)
    }
}
