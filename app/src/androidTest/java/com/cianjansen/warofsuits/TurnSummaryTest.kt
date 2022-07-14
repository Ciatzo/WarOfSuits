package com.cianjansen.warofsuits

import android.os.Parcel
import com.cianjansen.warofsuits.model.PlayingCard
import com.cianjansen.warofsuits.model.TurnSummary
import org.junit.Assert
import org.junit.Test

class TurnSummaryTest {
    /**
     * Tests whether a turnSummary retains fields when parcelized
     */
    @Test
    fun constructFromParcel() {
        val summaryIn = TurnSummary(
            PlayingCard(PlayingCard.Suit.HEARTS, PlayingCard.Rank.KING),
            PlayingCard(PlayingCard.Suit.DIAMONDS, PlayingCard.Rank.QUEEN),
            true
        )

        val parcel = Parcel.obtain()
        summaryIn.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)
        val summaryOut = TurnSummary.createFromParcel(parcel)

        Assert.assertEquals(summaryIn.first, summaryOut.first)
        Assert.assertEquals(summaryIn.second, summaryOut.second)
        Assert.assertEquals(summaryIn.yours, summaryOut.yours)
    }
}