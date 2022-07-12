package com.cianjansen.warofsuits.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.R
import com.cianjansen.warofsuits.databinding.ActivityGameBinding
import com.cianjansen.warofsuits.model.PlayingCard
import com.cianjansen.warofsuits.ui.victory.VictoryActivity

class GameActivity : AppCompatActivity(), GameContract.View {
    private lateinit var binding: ActivityGameBinding

    private lateinit var presenter: GameContract.Presenter

    companion object {
        private const val ANIMATION_END_ALPHA = 0f

        private const val ANIMATION_HIGHLIGHT_DURATION = 800L / 4

        private const val ANIMATION_MOVE_DURATION = 1000L / 4

        private const val ANIMATION_RESET_DURATION = 0L

        private const val ANIMATION_RESET_FACTOR = 0f

        private const val ANIMATION_START_ALPHA = 1f

        private const val ANIMATION_X_MOVE_FACTOR = 2

        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }

    override fun hideCards(yours: Boolean) {
        val translationX = if (yours) {
            binding.pcvYours.width.toFloat()
        } else {
            -binding.pcvYours.width.toFloat()
        }

        val translationYYours = if (yours) {
            binding.pcvYours.height.toFloat()
        } else {
            -ANIMATION_X_MOVE_FACTOR * binding.pcvYours.height.toFloat()
        }

        val translationYOpponent = if (yours) {
            ANIMATION_X_MOVE_FACTOR * binding.pcvYours.height.toFloat()
        } else {
            -binding.pcvYours.height.toFloat()
        }

        val yourAnim = binding.pcvYours.animate()
            .translationY(translationYYours)
            .translationX(translationX)
            .alpha(ANIMATION_END_ALPHA)
        yourAnim.duration = ANIMATION_MOVE_DURATION
        yourAnim.startDelay = ANIMATION_HIGHLIGHT_DURATION

        yourAnim.withEndAction {
            val endAnim = binding.pcvYours.animate()
                .translationY(ANIMATION_RESET_FACTOR)
                .translationX(ANIMATION_RESET_FACTOR)
            endAnim.duration = ANIMATION_RESET_DURATION
            endAnim.startDelay = ANIMATION_RESET_DURATION

            binding.btDrawCardYours.isEnabled = true
            binding.btDrawCardOpponent.isEnabled = true
        }

        val opponentAnim = binding.pcvOpponent.animate()
            .translationY(translationYOpponent)
            .translationX(translationX)
            .alpha(ANIMATION_END_ALPHA)
        opponentAnim.duration = ANIMATION_MOVE_DURATION
        opponentAnim.startDelay = ANIMATION_HIGHLIGHT_DURATION

        opponentAnim.withEndAction {
            val endAnim = binding.pcvOpponent.animate()
                .translationY(ANIMATION_RESET_FACTOR)
                .translationX(ANIMATION_RESET_FACTOR)
            endAnim.duration = ANIMATION_RESET_DURATION
            endAnim.startDelay = ANIMATION_RESET_DURATION
        }
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
    }

    override fun setPresenter(presenter: GameContract.Presenter) {
        this.presenter = presenter
    }

    override fun showCard(card: PlayingCard?, yours: Boolean) {
        if (yours) {
            binding.pcvYours.showCard(card)
            binding.pcvYours.alpha = ANIMATION_START_ALPHA
        } else {

            binding.pcvOpponent.showCard(card)
            binding.pcvOpponent.alpha = ANIMATION_START_ALPHA
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

    override fun showVictoryActivity(yourScore: Int) {
        startActivity(VictoryActivity.newIntent(this, yourScore))
        finish()
    }
}