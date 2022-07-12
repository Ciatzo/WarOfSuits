package com.cianjansen.warofsuits.ui.victory

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.R
import com.cianjansen.warofsuits.databinding.ActivityVictoryBinding

class VictoryActivity : AppCompatActivity(), VictoryContract.View {
    private lateinit var binding: ActivityVictoryBinding

    private lateinit var presenter: VictoryContract.Presenter

    private var yourScore: Int = 0

    companion object {
        private const val EXTRA_YOUR_SCORE = "EXTRA_YOUR_SCORE"

        fun newIntent(context: Context, yourScore: Int): Intent {
            val intent = Intent(context, VictoryActivity::class.java)
            intent.putExtra(EXTRA_YOUR_SCORE, yourScore)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVictoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPresenter(VictoryPresenter(this))

        yourScore = intent.getIntExtra(EXTRA_YOUR_SCORE, 0)
        presenter.setWinner(yourScore)

        binding.btExitOpponent.setOnClickListener {
            finish()
        }

        binding.btExitYours.setOnClickListener {
            finish()
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