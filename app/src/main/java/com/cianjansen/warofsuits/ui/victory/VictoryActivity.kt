package com.cianjansen.warofsuits.ui.victory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.R
import com.cianjansen.warofsuits.databinding.ActivityVictoryBinding
import com.cianjansen.warofsuits.model.TurnSummary
import com.cianjansen.warofsuits.ui.summary.SummaryActivity

class VictoryActivity : AppCompatActivity(), VictoryContract.View {
    private lateinit var binding: ActivityVictoryBinding

    private lateinit var presenter: VictoryContract.Presenter

    private var turnList: ArrayList<TurnSummary>? = null

    private var yourScore: Int = 0

    companion object {
        private const val EXTRA_TURN_LIST = "EXTRA_TURN_LIST"

        private const val EXTRA_YOUR_SCORE = "EXTRA_YOUR_SCORE"

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

        yourScore = intent.getIntExtra(EXTRA_YOUR_SCORE, 0)
        turnList = intent.getParcelableArrayListExtra<TurnSummary>(EXTRA_TURN_LIST)
        presenter.setWinner(yourScore)

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

    override fun showResult(yourScore: Int, opponentScore: Int, result:GameResult) {
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

    enum class GameResult {
        YOU_WIN,
        OPPONENT_WIN,
        DRAW
    }
}