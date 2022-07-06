package com.cianjansen.warofsuits.model

class PlayingCard(val suit: Suit, val rank: Rank) {
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
}