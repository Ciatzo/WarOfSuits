package com.cianjansen.warofsuits.ui.activity.victory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.R
import com.cianjansen.warofsuits.databinding.ActivityVictoryBinding
import com.cianjansen.warofsuits.model.TurnSummary
import com.cianjansen.warofsuits.ui.activity.summary.SummaryActivity

/**
 * View class for Victory Activity. Shows visually which player won and the final score
 */
class VictoryActivity : AppCompatActivity(), VictoryContract.View {
    private lateinit var binding: ActivityVictoryBinding

    private lateinit var presenter: VictoryContract.Presenter

    /*
    List of all the turns that took place in the game
     */
    private var turnList: ArrayList<TurnSummary>? = null

    companion object {
        private const val EXTRA_TURN_LIST = "EXTRA_TURN_LIST"

        private const val EXTRA_YOUR_SCORE = "EXTRA_YOUR_SCORE"

        /**
         * Creates intent to VictoryActivity
         * @param turnList the list of turns that took place during the game, to be passed on to
         * the summaryActivity
         * @param yourScore the score obtained by the "you" player
         */
        fun newIntent(
            context: Context,
            yourScore: Int,
            turnList: ArrayList<TurnSummary>
        ): Intent {
            val intent = Intent(context, VictoryActivity::class.java)
            intent.putExtra(EXTRA_YOUR_SCORE, yourScore)
            intent.putParcelableArrayListExtra(EXTRA_TURN_LIST, turnList)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVictoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPresenter(VictoryPresenter(this))

        turnList = intent.getParcelableArrayListExtra<TurnSummary>(EXTRA_TURN_LIST)
        presenter.setWinner(intent.getIntExtra(EXTRA_YOUR_SCORE, 0))

        binding.btExitOpponent.setOnClickListener {
            finish()
        }

        binding.btExitYours.setOnClickListener {
            finish()
        }

        binding.btSummaryYours.setOnClickListener {
            turnList?.let {
                startActivity(SummaryActivity.newIntent(this, it))
            } ?: finish()
        }

        binding.btSummaryOpponent.setOnClickListener {
            turnList?.let {
                startActivity(SummaryActivity.newIntent(this, it))
            } ?: finish()
        }
    }

    override fun setPresenter(presenter: VictoryContract.Presenter) {
        this.presenter = presenter
    }

    /**
     * Shows which player won the game using the @GameResult enum class, and the scores
     * gained by the different players
     * @param yourScore the score achieved by the "you" player
     * @param opponentScore the score achieved by the "opponent" player
     * @param result the GameResult of this game
     */
    override fun showResult(yourScore: Int, opponentScore: Int, result: GameResult) {
        when(result) {
            GameResult.YOU_WIN -> {
                binding.clVictoryYours.setBackgroundColor(getColor(R.color.green_victory))
                binding.clVictoryOpponent.setBackgroundColor(getColor(R.color.red_defeat))
                binding.tvVictoryYours.text =
                    getString(R.string.victory_activity_victory ) + "$yourScore - $opponentScore"
                binding.tvVictoryOpponent.text =
                    getString(R.string.victory_activity_defeat ) + "$opponentScore - $yourScore"
            }
            GameResult.OPPONENT_WIN -> {
                binding.clVictoryOpponent.setBackgroundColor(getColor(R.color.green_victory))
                binding.clVictoryYours.setBackgroundColor(getColor(R.color.red_defeat))
                binding.tvVictoryYours.text =
                    getString(R.string.victory_activity_defeat ) + "$yourScore - $opponentScore"
                binding.tvVictoryOpponent.text =
                    getString(R.string.victory_activity_victory ) + "$opponentScore - $yourScore"
            }
            GameResult.DRAW -> {
                binding.clVictoryYours.setBackgroundColor(getColor(R.color.orange_draw))
                binding.clVictoryOpponent.setBackgroundColor(getColor(R.color.orange_draw))
                binding.tvVictoryYours.text =
                    getString(R.string.victory_activity_draw ) + "$yourScore - $opponentScore"
                binding.tvVictoryOpponent.text =
                    getString(R.string.victory_activity_draw ) + "$opponentScore - $yourScore"
            }
        }
    }

    /**
     * Enum class for the different results a game of War of Suits can have, contains YOU_WIN,
     * OPPONENT_WIN and DRAW
     */
    enum class GameResult {
        YOU_WIN,
        OPPONENT_WIN,
        DRAW
    }
}
