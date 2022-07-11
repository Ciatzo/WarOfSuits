package com.cianjansen.warofsuits.ui.game

import com.cianjansen.warofsuits.model.Deck

class GamePresenter(private var view: GameContract.View) : GameContract.Presenter {

    private var deck: Deck = Deck(true)

    private var score = 0

    override fun drawCards() {

        if (deck.cards.isNotEmpty()) {
            val card1 = deck.cards.removeFirst()
            val card2 = deck.cards.removeLast()

            score += deck.compareCards(card1, card2)
            view.showCard(card1, true)
            view.showCard(card2, false)
            view.showScore(score)
        }
    }
}