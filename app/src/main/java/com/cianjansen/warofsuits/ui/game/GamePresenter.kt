package com.cianjansen.warofsuits.ui.game

import com.cianjansen.warofsuits.model.Deck
import com.cianjansen.warofsuits.model.PlayingCard

class GamePresenter(private var view: GameContract.View) : GameContract.Presenter {

    private var deck: Deck = Deck(true)

    private var yourCard: PlayingCard? = null

    private var yourScore = 0

    private var opponentCard: PlayingCard? = null

    private var opponentScore = 0

    override fun drawCard(yours: Boolean) {

        if (deck.cards.isNotEmpty()) {
            if (yours && yourCard == null) {
                yourCard = deck.cards.removeFirst()
            } else if (!yours && opponentCard == null) {
                opponentCard = deck.cards.removeLast()
            }

            showCards()

            yourCard?.let { yc ->
                opponentCard?.let { oc ->
                    if (deck.compareCards(yc, oc) > 0) {
                        yourScore++
                    } else {
                        opponentScore++
                    }

                    yourCard = null
                    opponentCard = null

                    resetCards()
                }
            }

            view.showScore(yourScore, opponentScore)
        }
    }

    private fun resetCards() {

    }

    private fun showCards() {
        view.showCard(yourCard, true)
        view.showCard(opponentCard, false)
    }
}