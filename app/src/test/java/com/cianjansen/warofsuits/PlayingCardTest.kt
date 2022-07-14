package com.cianjansen.warofsuits

import com.cianjansen.warofsuits.model.PlayingCard
import org.junit.Assert.assertEquals
import org.junit.Test

class PlayingCardTest {
    @Test
    fun toStringTest() {
        val card = PlayingCard(PlayingCard.Suit.HEARTS, PlayingCard.Rank.TEN)
        assertEquals(card.toString(), "♥10")
    }
}
