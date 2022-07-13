package com.cianjansen.warofsuits.ui.game

import com.cianjansen.warofsuits.model.TurnSummary
import com.cianjansen.warofsuits.model.Deck
import com.cianjansen.warofsuits.model.PlayingCard

/**
 * Presenter class for the game activity. Handles game logic and data
 */
class GamePresenter(private var view: GameContract.View) : GameContract.Presenter {
    /*
    The deck to be used for this game. Contains 52 poker cards at the start, and a randomly
    determined suitOrder to break ties
     */
    private var deck: Deck = Deck(true)

    private var yourCard: PlayingCard? = null

    private var yourScore = 0

    private var opponentCard: PlayingCard? = null

    private var opponentScore = 0

    private var turnList: ArrayList<TurnSummary> = ArrayList()

    /**
     * Draws a new card from the draw pile for the relevant player
     * @param yours used to determine whether the card should be drawn for the "you" player or the
     * "opponent" player. In case of the "you" player, the card is drawn from the start of the
     * deck. In case of the "opponent" player, it is drawn from the end of the deck. This way, the
     * game is pre-determined the moment the deck is shuffled (as described in the tech test spec)
     */
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

        // Comparing of cards happens when both cards are non-null (aka have been drawn)
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

                /*
                When a turn is over (both players have drawn a card), the turn gets added to the
                turnList to be used for the post-game summary
                 */
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

    /**
     * Ends the game when one of the players forfeits
     * @param yours used to determine whether the "you" player or the "opponent" player forfeited.
     * In case the opponent forfeited, the remaining cards in the deck are put in the "you"
     * player discard pile. Since the "opponent" player score is determined as the inverse of the
     * "you" player, this is not necessary when the "you" player forfeits
     */
    override fun onGameForfeited(yours: Boolean) {
        if (yours) {
            view.startVictoryActivity(yourScore, turnList)
        } else {
            view.startVictoryActivity(yourScore + deck.cards.size, turnList)
        }
    }

    /**
     * Re-initializes the deck and resets cards and scores if the game is restarted
     */
    override fun onGameRestarted() {
        deck = Deck(true)
        yourCard = null
        opponentCard = null
        yourScore = 0
        opponentScore = 0
        showCards()
        view.showScore(yourScore, opponentScore)
        onViewCreated()
    }

    /**
     * Shows the suit order (for breaking ties) to the players at the start of a new game
     */
    override fun onViewCreated() {
        view.showSuitOrder(deck.suitOrder.joinToString(separator = ">"))
    }

    /**
     * Shows the cards of both players
     */
    private fun showCards() {
        view.showCard(yourCard, true)
        view.showCard(opponentCard, false)
    }
}
