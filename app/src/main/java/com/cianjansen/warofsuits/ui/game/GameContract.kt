package com.cianjansen.warofsuits.ui.game

import com.cianjansen.warofsuits.model.TurnSummary
import com.cianjansen.warofsuits.ui.base.BasePresenter
import com.cianjansen.warofsuits.ui.base.BaseView
import com.cianjansen.warofsuits.model.PlayingCard

class GameContract {
    interface Presenter : BasePresenter {
        fun drawCard(yours: Boolean)

        fun onGameForfeited(yours: Boolean)

        fun onGameRestarted()

        fun onViewCreated()
    }

    interface View : BaseView<Presenter> {
        fun hideCards(yours: Boolean)

        fun showCard(card: PlayingCard?, yours: Boolean)

        fun showScore(yourScore: Int, opponentScore: Int)

        fun showSuitOrder(suitOrder: String)

        fun startVictoryActivity(yourScore: Int, turnList: ArrayList<TurnSummary>)
    }
}