package com.cianjansen.warofsuits

import com.cianjansen.warofsuits.model.Deck
import com.cianjansen.warofsuits.model.PlayingCard
import org.junit.Test
import org.junit.Assert.*

class DeckTest {

    @Test
    fun deckIsSorted() {
        val deck = Deck(false)

        assertEquals(deck.cards[0].suit, PlayingCard.Suit.HEARTS)
        assertEquals(deck.cards[0].rank, PlayingCard.Rank.TWO)
        assertEquals(deck.cards[12].suit, PlayingCard.Suit.HEARTS)
        assertEquals(deck.cards[12].rank, PlayingCard.Rank.ACE)
    }

    @Test
    fun compareRankSameSuit() {
        val deck = Deck(false)

        assertEquals(deck.compareCards(
            PlayingCard(PlayingCard.Suit.HEARTS, PlayingCard.Rank.FIVE),
            PlayingCard(PlayingCard.Suit.HEARTS, PlayingCard.Rank.THREE)
        ), 1)

        assertEquals(deck.compareCards(
            PlayingCard(PlayingCard.Suit.HEARTS, PlayingCard.Rank.FIVE),
            PlayingCard(PlayingCard.Suit.HEARTS, PlayingCard.Rank.KING)
        ), -1)
    }

    @Test
    fun compareRankDiffSuit() {
        val deck = Deck(false)

        assertEquals(deck.compareCards(
            PlayingCard(PlayingCard.Suit.HEARTS, PlayingCard.Rank.FIVE),
            PlayingCard(PlayingCard.Suit.DIAMONDS, PlayingCard.Rank.THREE)
        ), 1)

        assertEquals(deck.compareCards(
            PlayingCard(PlayingCard.Suit.HEARTS, PlayingCard.Rank.FIVE),
            PlayingCard(PlayingCard.Suit.CLUBS, PlayingCard.Rank.KING)
        ), 1)
    }

    @Test
    fun compareSuitSameRank() {
        val deck = Deck(false, randomSuitOrder = false)

        assertEquals(deck.compareCards(
            PlayingCard(PlayingCard.Suit.HEARTS, PlayingCard.Rank.FIVE),
            PlayingCard(PlayingCard.Suit.DIAMONDS, PlayingCard.Rank.FIVE)
        ), 1)

        assertEquals(deck.compareCards(
            PlayingCard(PlayingCard.Suit.CLUBS, PlayingCard.Rank.FIVE),
            PlayingCard(PlayingCard.Suit.DIAMONDS, PlayingCard.Rank.FIVE)
        ), -1)
    }
}