package com.cianjansen.warofsuits.model

/**
 * A deck of cards
 * @param shuffled whether the list of cards should be shuffled or in order
 * @param randomSuitOrder whether the priority of different suits should be random or ordered
 */
class Deck(shuffled: Boolean, randomSuitOrder: Boolean = true) {

    var cards: MutableList<PlayingCard> = ArrayList()

    // The priority order of the different suits to use as a tie-breaker. Determined randomly
    val suitOrder: List<PlayingCard.Suit>

    init {
        for (suit in PlayingCard.Suit.values()) {
            for (rank in PlayingCard.Rank.values()) {
                cards.add(PlayingCard(suit, rank))
            }
        }

        if (shuffled) {
            cards.shuffle()
        }

        // Non-random suitOrder used for testing
        suitOrder = if (randomSuitOrder) {
            PlayingCard.Suit.values().toList().shuffled()
        } else {
            PlayingCard.Suit.values().toList()
        }
    }

    /**
     * Used to compare cards to see which is highest. Compares on rank first. In case of equal rank
     * compares on pre-determined suitOrder for this deck
     * @param card1 the card of the "you" player
     * @param card2 the card of the "opponent" player
     * @return 1 if card1 is higher, -1 if card2 is higher
     */
    fun compareCards(card1: PlayingCard, card2: PlayingCard): Int {
        return when {
            card1.rank  > card2.rank -> 1
            card1.rank < card2.rank -> -1
            suitOrder.indexOf(card1.suit) < suitOrder.indexOf(card2.suit) -> 1
            else -> -1
        }
    }
}
