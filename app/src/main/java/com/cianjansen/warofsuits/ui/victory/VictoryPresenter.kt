package com.cianjansen.warofsuits.ui.victory

/**
 * Presenter for the Victory activity. Determines what player won based on the score of the "you"
 * player
 */
class VictoryPresenter(private var view: VictoryContract.View) : VictoryContract.Presenter {
    companion object {
        // The total number of cards in a deck
        private const val CARDS_IN_DECK = 52
    }

    /**
     * Determines which player won
     * @param yourScore the score the "you" player got. The score of the opponent player is
     * determined by subtracting the yourScore from the CARDS_IN_DECK constant
     */
    override fun setWinner(yourScore: Int) {
        val opponentScore = CARDS_IN_DECK - yourScore

        if (yourScore > opponentScore) {
            view.showResult(yourScore, opponentScore, VictoryActivity.GameResult.YOU_WIN)
        } else if (opponentScore > yourScore) {
            view.showResult(yourScore, opponentScore, VictoryActivity.GameResult.OPPONENT_WIN)
        } else {
            view.showResult(yourScore, opponentScore, VictoryActivity.GameResult.DRAW)
        }
    }

}
