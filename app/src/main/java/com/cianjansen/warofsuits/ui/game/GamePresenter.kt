package com.cianjansen.warofsuits.ui.game

import com.cianjansen.warofsuits.model.TurnSummary
import com.cianjansen.warofsuits.model.Deck
import com.cianjansen.warofsuits.model.PlayingCard

class GamePresenter(private var view: GameContract.View) : GameContract.Presenter {

    private var deck: Deck = Deck(true)

    private var yourCard: PlayingCard? = null

    private var yourScore = 0

    private var opponentCard: PlayingCard? = null

    private var opponentScore = 0

    private var turnList: ArrayList<TurnSummary> = ArrayList()

    override fun drawCard(yours: Boolean) {
        if (deck.cards.isEmpty()) {
            view.startVictoryActivity(yourScore, turnList)
        }

        if (yours && yourCard == null) {
            yourCard = deck.cards.removeFirst()
        } else if (!yours && opponentCard == null) {
            opponentCard = deck.cards.removeLast()
        }

        showCards()

        yourCard?.let { yc ->
            opponentCard?.let { oc ->
                val yourWin = deck.compareCards(yc, oc) > 0

                if (yourWin) {
                    yourScore += 2
                    view.showWinner(true)
                } else {
                    opponentScore += 2
                    view.showWinner(false)
                }

                turnList.add(TurnSummary(yc, oc, yourWin))
                yourCard = null
                opponentCard = null
            }
        }

        view.showScore(yourScore, opponentScore)

        if (deck.cards.isEmpty()) {
            view.startVictoryActivity(yourScore, turnList)
        }
    }

    override fun onGameForfeited(yours: Boolean) {
        if (yours) {
            view.startVictoryActivity(yourScore, turnList)
        } else {
            view.startVictoryActivity(yourScore + deck.cards.size, turnList)
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
