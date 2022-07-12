package com.cianjansen.warofsuits.model

class Deck(shuffled: Boolean, randomSuitOrder: Boolean = true) {

    var cards: MutableList<PlayingCard> = ArrayList(DECK_CAPACITY)

    val suitOrder: List<PlayingCard.Suit>

    companion object {
        private const val DECK_CAPACITY = 52
    }

    init {
        for (suit in PlayingCard.Suit.values()) {
            for (rank in PlayingCard.Rank.values()) {
                cards.add(PlayingCard(suit, rank))
            }
        }

        if (shuffled) {
            cards.shuffle()
        }

        suitOrder = if (randomSuitOrder) {
            PlayingCard.Suit.values().toList().shuffled()
        } else {
            PlayingCard.Suit.values().toList()
        }
    }

    fun compareCards(card1: PlayingCard, card2: PlayingCard): Int {
        return if (card1.rank > card2.rank) {
            1
        } else if (card1.rank < card2.rank) {
            -1
        } else  {
            if (suitOrder.indexOf(card1.suit) < suitOrder.indexOf(card2.suit)) {
                1
            } else {
                -1
            }
        }
    }
}