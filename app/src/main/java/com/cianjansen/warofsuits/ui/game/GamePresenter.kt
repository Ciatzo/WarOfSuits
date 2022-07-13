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
                        yourScore += 2
                        view.hideCards(true)
                    } else {
                        opponentScore += 2
                        view.hideCards(false)
                    }

                    yourCard = null
                    opponentCard = null
                }
            }

            view.showScore(yourScore, opponentScore)

            if (deck.cards.isEmpty()) {
                view.startVictoryActivity(yourScore)
            }
        }
    }

    override fun onGameForfeited(yours: Boolean) {
        if (yours) {
            view.startVictoryActivity(yourScore)
        } else {
            view.startVictoryActivity(yourScore + deck.cards.size)
        }
    }

    override fun onGameRestarted() {
        deck = Deck(true)
        yourCard = null
        opponentCard = null
        yourScore = 0
        opponentScore = 0
        showCards()
        view.showScore(yourScore, opponentScore)
        view.showSuitOrder(deck.suitOrder.joinToString(separator = ">"))
    }

    override fun onViewCreated() {
        view.showSuitOrder(deck.suitOrder.joinToString(separator = ">"))
    }

    private fun showCards() {
        view.showCard(yourCard, true)
        view.showCard(opponentCard, false)
    }
}