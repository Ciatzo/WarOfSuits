package com.cianjansen.warofsuits

import android.os.Parcel
import com.cianjansen.warofsuits.model.PlayingCard
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * AndroidTests for the PlayingCard class
 */
class PlayingCardTest {
    /**
     * Tests whether a card retains suit and rank when parceled and recreated
     */
    @Test
    fun constructFromParcel() {
        val cardIn = PlayingCard(PlayingCard.Suit.HEARTS, PlayingCard.Rank.KING)

        val parcel = Parcel.obtain()
        cardIn.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val cardOut = PlayingCard.createFromParcel(parcel)
        assertEquals(cardIn, cardOut)
    }
}