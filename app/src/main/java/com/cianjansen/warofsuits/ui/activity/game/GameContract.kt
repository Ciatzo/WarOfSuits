package com.cianjansen.warofsuits.ui.activity.game

import com.cianjansen.warofsuits.model.TurnSummary
import com.cianjansen.warofsuits.ui.activity.base.BasePresenter
import com.cianjansen.warofsuits.ui.activity.base.BaseView
import com.cianjansen.warofsuits.model.PlayingCard

interface GameContract {
    interface Presenter : BasePresenter {
        fun drawCard(yours: Boolean)

        fun onGameForfeited(yours: Boolean)

        fun onGameRestarted()

        fun onViewCreated()
    }

    interface View : BaseView<Presenter> {
        fun showWinner(yours: Boolean)

        fun showCard(card: PlayingCard?, yours: Boolean)

        fun showScore(yourScore: Int, opponentScore: Int)

        fun showSuitOrder(suitOrder: String)

        fun startVictoryActivity(yourScore: Int, turnList: ArrayList<TurnSummary>)
    }
}
