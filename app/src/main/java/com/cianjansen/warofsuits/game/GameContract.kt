package com.cianjansen.warofsuits.game

import com.cianjansen.warofsuits.base.BasePresenter
import com.cianjansen.warofsuits.base.BaseView
import com.cianjansen.warofsuits.model.PlayingCard

class GameContract {
    interface Presenter : BasePresenter {
        fun drawCards()
    }

    interface View : BaseView<Presenter> {
        fun showCard(card: PlayingCard, yours: Boolean)

        fun showScore(score: Int)
    }
}