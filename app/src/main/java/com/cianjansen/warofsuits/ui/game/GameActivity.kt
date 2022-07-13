package com.cianjansen.warofsuits.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.R
import com.cianjansen.warofsuits.databinding.ActivityGameBinding
import com.cianjansen.warofsuits.model.PlayingCard
import com.cianjansen.warofsuits.model.TurnSummary
import com.cianjansen.warofsuits.ui.TwoOptionDialog
import com.cianjansen.warofsuits.ui.victory.VictoryActivity


class GameActivity : AppCompatActivity(), GameContract.View {
    private lateinit var binding: ActivityGameBinding

    private lateinit var presenter: GameContract.Presenter

    companion object {
        private const val ANIMATION_END_ALPHA = 0f

        private const val ANIMATION_HIGHLIGHT_DURATION = 800L / 30

        private const val ANIMATION_GROW_SCALE = 1.3f

        private const val ANIMATION_MOVE_DURATION = 1000L / 20

        private const val ANIMATION_RESET_DURATION = 0L

        private const val ANIMATION_RESET_FACTOR = 0f

        private const val ANIMATION_RESET_SCALE = 1f

        private const val ANIMATION_START_ALPHA = 1f

        private const val ANIMATION_X_MOVE_FACTOR = 2

        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }

    override fun showWinner(yours: Boolean) {
        val winAnim = if (yours) {
            binding.pcvYours.animate()
        } else {
            binding.pcvOpponent.animate()
        }

        winAnim
            .scaleX(ANIMATION_GROW_SCALE)
            .scaleY(ANIMATION_GROW_SCALE)
            .duration = ANIMATION_HIGHLIGHT_DURATION

        winAnim.withEndAction {
            val resetScaleAnim = if (yours) {
                binding.pcvYours.animate()
            } else {
                binding.pcvOpponent.animate()
            }

            resetScaleAnim
                .scaleX(ANIMATION_RESET_SCALE)
                .scaleY(ANIMATION_RESET_SCALE)
                .duration = ANIMATION_HIGHLIGHT_DURATION

            resetScaleAnim.withEndAction {
                hideCards(yours)
            }
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.game_activity_quit_title))
            .setMessage(getString(R.string.game_activity_quit))
            .setPositiveButton(getString(R.string.game_activity_quit_yes)) { _, _ -> finish() }
            .setNegativeButton(getString(R.string.game_activity_quit_no), null)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPresenter(GamePresenter(this))
        presenter.onViewCreated()

        binding.btDrawCardYours.setOnClickListener {
            presenter.drawCard(true)
            binding.btDrawCardYours.isEnabled = false
        }

        binding.btDrawCardOpponent.setOnClickListener {
            presenter.drawCard(false)
            binding.btDrawCardOpponent.isEnabled = false
        }

        binding.btForfeitYours.setOnClickListener { showForfeitDialog(true) }
        binding.btForfeitOpponent.setOnClickListener { showForfeitDialog(false) }

        binding.btRestartYours.setOnClickListener { showRestartDialog(true) }
        binding.btRestartOpponent.setOnClickListener { showRestartDialog(false) }
    }

    override fun setPresenter(presenter: GameContract.Presenter) {
        this.presenter = presenter
    }

    override fun showCard(card: PlayingCard?, yours: Boolean) {
        if (yours) {
            binding.pcvYours.showCard(card)
            binding.pcvYours.alpha = ANIMATION_START_ALPHA

            if (card == null) {
                binding.btDrawCardYours.isEnabled = true
            }
        } else {
            binding.pcvOpponent.showCard(card)
            binding.pcvOpponent.alpha = ANIMATION_START_ALPHA

            if (card == null) {
                binding.btDrawCardOpponent.isEnabled = true
            }
        }
    }

    override fun showScore(yourScore: Int, opponentScore: Int) {
        binding.tvDiscardYours.text = yourScore.toString()
        binding.tvDiscardOpponent.text = opponentScore.toString()
    }

    override fun showSuitOrder(suitOrder: String) {
        val suitOrderString = getString(R.string.suit_order) + "\n" + suitOrder
        binding.tvSuitOrderOpponent.text = suitOrderString
        binding.tvSuitOrderYours.text = suitOrderString
    }

    override fun startVictoryActivity(yourScore: Int, turnList: ArrayList<TurnSummary>) {
        startActivity(VictoryActivity.newIntent(this, yourScore, turnList))
        finish()
    }

    private fun hideCards(yours: Boolean) {
        val translationX = if (yours) {
            binding.pcvYours.width.toFloat()
        } else {
            -binding.pcvYours.width.toFloat()
        }

        val yTranslationYourCard = if (yours) {
            binding.pcvYours.height.toFloat()
        } else {
            -ANIMATION_X_MOVE_FACTOR * binding.pcvYours.height.toFloat()
        }

        val yTranslationOpponentCard = if (yours) {
            ANIMATION_X_MOVE_FACTOR * binding.pcvYours.height.toFloat()
        } else {
            -binding.pcvYours.height.toFloat()
        }

        val yourCardAnimation = binding.pcvYours.animate()
            .translationY(yTranslationYourCard)
            .translationX(translationX)
            .alpha(ANIMATION_END_ALPHA)
        yourCardAnimation.duration = ANIMATION_MOVE_DURATION

        yourCardAnimation.withEndAction {
            val endAnim = binding.pcvYours.animate()
                .translationY(ANIMATION_RESET_FACTOR)
                .translationX(ANIMATION_RESET_FACTOR)
            endAnim.duration = ANIMATION_RESET_DURATION
            endAnim.startDelay = ANIMATION_RESET_DURATION

            binding.btDrawCardYours.isEnabled = true
            binding.btDrawCardOpponent.isEnabled = true
        }

        val opponentCardAnimation = binding.pcvOpponent.animate()
            .translationY(yTranslationOpponentCard)
            .translationX(translationX)
            .alpha(ANIMATION_END_ALPHA)
        opponentCardAnimation.duration = ANIMATION_MOVE_DURATION

        opponentCardAnimation.withEndAction {
            val endAnim = binding.pcvOpponent.animate()
                .translationY(ANIMATION_RESET_FACTOR)
                .translationX(ANIMATION_RESET_FACTOR)
            endAnim.duration = ANIMATION_RESET_DURATION
            endAnim.startDelay = ANIMATION_RESET_DURATION
        }
    }

    private fun showForfeitDialog(yours: Boolean) {
        val onPositive = {
            presenter.onGameForfeited(yours)
        }

        TwoOptionDialog(
            this,
            !yours,
            onPositive,
            getString(R.string.game_activity_forfeit),
            getString(R.string.game_activity_forfeit_yes),
            getString(R.string.game_activity_forfeit_no)
        ).show()
    }

    private fun showRestartDialog(yours: Boolean) {
        val onPositive = {
            presenter.onGameRestarted()
        }

        TwoOptionDialog(
            this,
            !yours,
            onPositive,
            getString(R.string.game_activity_restart),
            getString(R.string.game_activity_restart_yes),
            getString(R.string.game_activity_restart_no)
        ).show()
    }
}
