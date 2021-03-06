package com.cianjansen.warofsuits.model

import android.os.Parcel
import android.os.Parcelable

/**
 * An object to hold a summary of a turn played in the card game
 * @param first the PlayingCard played by the "you" player
 * @param second the PlayingCard played by the "opponent" player
 * @param yours boolean describing whether the "you" player won the turn
 */
class TurnSummary(val first: PlayingCard?, val second: PlayingCard?, val yours: Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(PlayingCard::class.java.classLoader),
        parcel.readParcelable(PlayingCard::class.java.classLoader),
        parcel.readBoolean()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(first, flags)
        parcel.writeParcelable(second, flags)
        parcel.writeBoolean(yours)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TurnSummary> {
        override fun createFromParcel(parcel: Parcel): TurnSummary {
            return TurnSummary(parcel)
        }

        override fun newArray(size: Int): Array<TurnSummary?> {
            return arrayOfNulls(size)
        }
    }
}
