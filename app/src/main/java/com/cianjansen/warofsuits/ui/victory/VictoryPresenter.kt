package com.cianjansen.warofsuits.ui.victory

class VictoryPresenter(private var view: VictoryContract.View) : VictoryContract.Presenter {
    companion object {
        private const val CARDS_IN_DECK = 52
    }

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