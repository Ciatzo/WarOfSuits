package com.cianjansen.warofsuits.model

import android.os.Parcel
import android.os.Parcelable

open class PlayingCard(val suit: Suit, val rank: Rank) : Parcelable {
    constructor(parcel: Parcel) :
            this(parcel.readSerializable() as Suit, parcel.readSerializable() as Rank)

    enum class Suit {
        HEARTS {
            override fun toString(): String {
                return "♥"
            }
               },
        DIAMONDS {
            override fun toString(): String {
                return "♦"
            }
        },
        SPADES {
            override fun toString(): String {
                return "♠"
            }
        },
        CLUBS {
            override fun toString(): String {
                return "♣"
            }
        }
    }

    enum class Rank {
        TWO {
            override fun toString(): String {
                return "2"
            }
               },
        THREE {
            override fun toString(): String {
                return "3"
            }
               },
        FOUR {
            override fun toString(): String {
                return "4"
            }
               },
        FIVE {
            override fun toString(): String {
                return "5"
            }
               },
        SIX {
            override fun toString(): String {
                return "6"
            }
               },
        SEVEN {
            override fun toString(): String {
                return "7"
            }
               },
        EIGHT {
            override fun toString(): String {
                return "8"
            }
               },
        NINE {
            override fun toString(): String {
                return "9"
            }
               },
        TEN {
            override fun toString(): String {
                return "10"
            }
               },
        JACK {
            override fun toString(): String {
                return "J"
            }
               },
        QUEEN {
            override fun toString(): String {
                return "Q"
            }
               },
        KING {
            override fun toString(): String {
                return "K"
            }
               },
        ACE {
            override fun toString(): String {
                return "A"
            }
               }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(suit)
        parcel.writeSerializable(rank)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayingCard> {
        override fun createFromParcel(parcel: Parcel): PlayingCard {
            return PlayingCard(parcel)
        }

        override fun newArray(size: Int): Array<PlayingCard?> {
            return arrayOfNulls(size)
        }
    }
}