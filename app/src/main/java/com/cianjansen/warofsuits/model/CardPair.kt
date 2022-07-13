package com.cianjansen.warofsuits.model

import android.os.Parcel
import android.os.Parcelable

class CardPair(val first: PlayingCard?, val second: PlayingCard?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(PlayingCard::class.java.classLoader),
        parcel.readParcelable(PlayingCard::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(first, flags)
        parcel.writeParcelable(second, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CardPair> {
        override fun createFromParcel(parcel: Parcel): CardPair {
            return CardPair(parcel)
        }

        override fun newArray(size: Int): Array<CardPair?> {
            return arrayOfNulls(size)
        }
    }
}